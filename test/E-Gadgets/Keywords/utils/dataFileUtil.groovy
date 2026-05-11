package utils
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.*

import java.util.LinkedHashMap
import java.util.Map


/**
 * Đọc/ghi Data.xlsx dạng sheet có 2 cột: Key | Value (hàng đầu là header).
 * Các TC Katalon dùng chung: getValue, setData, ensureKeyWithDefault, getSheetAsMap, ...
 */
public class dataFileUtil {

	private static final String COL_KEY = 'Key'
	private static final String COL_VALUE = 'Value'

	@Keyword
	def String resolvePath(String filePath) {
		File file = new File(filePath)
		if (file.isAbsolute()) {
			return file.getAbsolutePath()
		}
		String projectDir = RunConfiguration.getProjectDir()
		return new File(projectDir, filePath).getAbsolutePath()
	}

	private String getCellAsString(Cell cell) {
		if (cell == null) return null
		switch (cell.getCellType()) {
			case CellType.STRING:
				return cell.getStringCellValue()
			case CellType.NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue().format("yyyy-MM-dd HH:mm:ss")
				}
				Double d = cell.getNumericCellValue()
				return d == d.intValue() ? String.valueOf(d.intValue()) : d.toString()
			case CellType.BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue())
			case CellType.FORMULA:
				try {
					return cell.getStringCellValue()
				} catch (Exception e) {
					try {
						return String.valueOf(cell.getNumericCellValue())
					} catch (Exception ex) {
						return null
					}
				}
			case CellType.BLANK:
				return null
			default:
				return null
		}
	}

	/** Hàng bắt đầu dữ liệu: 1 nếu có header Key/Value, không thì 0 */
	private int getFirstDataRowIndex(Sheet sheet) {
		if (sheet == null || sheet.getRow(0) == null) {
			return 0
		}
		String h0 = getCellAsString(sheet.getRow(0).getCell(0))
		if (h0 != null && (COL_KEY.equalsIgnoreCase(h0.trim()) || 'key'.equalsIgnoreCase(h0.trim()))) {
			return 1
		}
		return 0
	}

	/**
	 * Đọc giá trị theo key (cột 0 = key, cột 1 = value). Trả về String hoặc null.
	 * Thay thế getData cũ (trước đây trả về nhầm Cell).
	 */
	@Keyword
	def String getValue(String filePath, String key, String sheetName) {
		String resolved = resolvePath(filePath)
		File file = new File(resolved)
		if (!file.exists() || key == null) {
			return null
		}

		FileInputStream fis = null
		XSSFWorkbook workbook = null
		try {
			fis = new FileInputStream(file)
			workbook = new XSSFWorkbook(fis)
			Sheet sheet = workbook.getSheet(sheetName)
			if (sheet == null) {
				return null
			}
			int start = getFirstDataRowIndex(sheet)
			for (int i = start; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i)
				if (row == null) {
					continue
				}
				String cellKey = getCellAsString(row.getCell(0))
				if (cellKey != null && cellKey.trim().equals(key.trim())) {
					String val = getCellAsString(row.getCell(1))
					return val != null ? val.trim() : null
				}
			}
			return null
		} catch (Exception e) {
			return null
		} finally {
			if (fis != null) try {
				fis.close()
			} catch (Exception ignore) {}
			if (workbook != null) try {
				workbook.close()
			} catch (Exception ignore) {}
		}
	}

	/** Tương thích script cũ gọi getData — ủy quyền sang getValue */
	@Keyword
	def String getData(String filePath, String key, String sheetName) {
		return getValue(filePath, key, sheetName)
	}

	/**
	 * Đọc key; nếu null hoặc rỗng thì ghi default vào sheet và trả về default.
	 * Dùng cho TC cần seed dữ liệu lần đầu (Login_Success, Register_Unsuccess, ...).
	 */
	@Keyword
	def String ensureKeyWithDefault(String filePath, String key, String defaultValue, String sheetName) {
		String v = getValue(filePath, key, sheetName)
		if (v == null || v.trim().isEmpty()) {
			setData(filePath, key, defaultValue, sheetName)
			return defaultValue
		}
		return v.trim()
	}

	/**
	 * Chỉ đọc, không ghi: trả về default nếu không có giá trị.
	 */
	@Keyword
	def String getOrDefault(String filePath, String key, String defaultValue, String sheetName) {
		String v = getValue(filePath, key, sheetName)
		if (v == null || v.trim().isEmpty()) {
			return defaultValue
		}
		return v.trim()
	}

	/**
	 * Đọc toàn bộ cặp key→value của sheet (bỏ qua header Key|Value).
	 */
	@Keyword
	def getSheetAsMap(String filePath, String sheetName) {
		Map map = new LinkedHashMap()
		String resolved = resolvePath(filePath)
		File file = new File(resolved)
		if (!file.exists()) {
			return map
		}
		FileInputStream fis = null
		XSSFWorkbook workbook = null
		try {
			fis = new FileInputStream(file)
			workbook = new XSSFWorkbook(fis)
			Sheet sheet = workbook.getSheet(sheetName)
			if (sheet == null) {
				return map
			}
			int start = getFirstDataRowIndex(sheet)
			for (int i = start; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i)
				if (row == null) {
					continue
				}
				String k = getCellAsString(row.getCell(0))
				if (k == null || k.trim().isEmpty()) {
					continue
				}
				String val = getCellAsString(row.getCell(1))
				map.put(k.trim(), val != null ? val : '')
			}
			return map
		} catch (Exception e) {
			return map
		} finally {
			if (fis != null) try {
				fis.close()
			} catch (Exception ignore) {}
			if (workbook != null) try {
				workbook.close()
			} catch (Exception ignore) {}
		}
	}

	@Keyword
	boolean sheetExists(String filePath, String sheetName) {
		String resolved = resolvePath(filePath)
		File file = new File(resolved)
		if (!file.exists()) {
			return false
		}
		FileInputStream fis = null
		XSSFWorkbook workbook = null
		try {
			fis = new FileInputStream(file)
			workbook = new XSSFWorkbook(fis)
			return workbook.getSheet(sheetName) != null
		} catch (Exception e) {
			return false
		} finally {
			if (fis != null) try {
				fis.close()
			} catch (Exception ignore) {}
			if (workbook != null) try {
				workbook.close()
			} catch (Exception ignore) {}
		}
	}

	@Keyword
	def setData(String filePath, String key, def value, String sheetName) {
		String valueToWrite = ""

		if (value == null) {
			valueToWrite = ""
		} else if (value instanceof String) {
			valueToWrite = value
		} else if (value.hasProperty("getText")) {
			try {
				valueToWrite = value.toString()
			} catch (Exception e) {
				valueToWrite = value.toString()
			}
		} else {
			valueToWrite = value.toString()
		}

		String resolved = resolvePath(filePath)
		File file = new File(resolved)
		XSSFWorkbook workbook = null
		Sheet sheet = null

		FileInputStream fis = null
		try {
			if (file.exists()) {
				fis = new FileInputStream(file)
				workbook = new XSSFWorkbook(fis)
				sheet = workbook.getSheet(sheetName)
				if (sheet == null) {
					sheet = workbook.createSheet(sheetName)
					Row header = sheet.createRow(0)
					header.createCell(0).setCellValue(COL_KEY)
					header.createCell(1).setCellValue(COL_VALUE)
				}
			} else {
				workbook = new XSSFWorkbook()
				sheet = workbook.createSheet(sheetName)
				Row header = sheet.createRow(0)
				header.createCell(0).setCellValue(COL_KEY)
				header.createCell(1).setCellValue(COL_VALUE)
			}
		} catch (Exception e) {
			if (workbook != null) try {
				workbook.close()
			} catch (Exception ignore) {}
			workbook = new XSSFWorkbook()
			sheet = workbook.createSheet(sheetName)
			Row header = sheet.createRow(0)
			header.createCell(0).setCellValue(COL_KEY)
			header.createCell(1).setCellValue(COL_VALUE)
		} finally {
			if (fis != null) try {
				fis.close()
			} catch (Exception ignore) {}
		}

		boolean found = false
		int startRow = getFirstDataRowIndex(sheet)

		String keyNorm = key != null ? key.trim() : ''
		for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i)
			if (row != null) {
				String cellKey = getCellAsString(row.getCell(0))
				if (cellKey != null && cellKey.trim().equals(keyNorm)) {
					Cell valueCell = row.getCell(1)
					if (valueCell == null) {
						valueCell = row.createCell(1)
					}
					valueCell.setCellValue(valueToWrite)
					found = true
					break
				}
			}
		}

		if (!found) {
			int newRowIndex = sheet.getLastRowNum() + 1
			Row newRow = sheet.createRow(newRowIndex)
			newRow.createCell(0).setCellValue(keyNorm)
			newRow.createCell(1).setCellValue(valueToWrite)
		}

		FileOutputStream fos = null
		try {
			fos = new FileOutputStream(file)
			workbook.write(fos)
		} finally {
			if (fos != null) try {
				fos.close()
			} catch (Exception ignore) {}
			if (workbook != null) try {
				workbook.close()
			} catch (Exception ignore) {}
		}
	}

	@Keyword
	def initializeDefaultData(String filePath) {
		String resolved = resolvePath(filePath)
		File file = new File(resolved)

		if (!file.exists()) {
			setData(filePath, 'register_email', 'testuser@example.com', 'Register_Success')
			setData(filePath, 'register_password', 'password123', 'Register_Success')
			setData(filePath, 'register_name', 'Test User', 'Register_Success')
			setData(filePath, 'register_phone', '0123456789', 'Register_Success')
			setData(filePath, 'register_address', '123 Test Street, Test City', 'Register_Success')

			setData(filePath, 'email', 'unsucc_base@example.com', 'Register_Unsuccess')
			setData(filePath, 'name', 'Lao Thanh Thao', 'Register_Unsuccess')
			setData(filePath, 'address', 'Ha Noi', 'Register_Unsuccess')
			setData(filePath, 'phone', '0123456789', 'Register_Unsuccess')
			setData(filePath, 'password', '123456', 'Register_Unsuccess')

			setData(filePath, 'email', 'admin@gmail.com', 'Login_Success')
			setData(filePath, 'password', '123456', 'Login_Success')

			setData(filePath, 'AddToCard_Tittle1', 'The Family Experiment', 'Sheet1')
			setData(filePath, 'AddToCard_Price1', '100000', 'Sheet1')
			setData(filePath, 'AddToCard_Quantity1', '2', 'Sheet1')

			setData(filePath, 'AddToCard_Tittle2', 'Product Title 2', 'Sheet1')
			setData(filePath, 'AddToCard_Price2', '150000', 'Sheet1')
			setData(filePath, 'AddToCard_Quantity2', '1', 'Sheet1')

			setData(filePath, 'Order_Code', 'ORDER001', 'Sheet1')
			setData(filePath, 'Order_Date', '2025-01-01', 'Sheet1')
			setData(filePath, 'Order_Total', '200000', 'Sheet1')
			setData(filePath, 'Order_Payment', 'COD', 'Sheet1')
			setData(filePath, 'Order_Status', 'PENDING', 'Sheet1')

			setData(filePath, 'OrderVNPay_Code', 'ORDER002', 'Sheet1')
			setData(filePath, 'OrderVNPay_Date', '2025-01-01', 'Sheet1')
			setData(filePath, 'OrderVNPay_Total', '200000', 'Sheet1')
			setData(filePath, 'OrderVNPay_Payment', 'VNPAY', 'Sheet1')
			setData(filePath, 'OrderVNPay_Status', 'PENDING', 'Sheet1')
		}
	}

	@Keyword
	def updateExcelTestResult(String filePath, String sheetName, String searchKeyword, String actualResult, String status) {
		String resolved = resolvePath(filePath)
		File file = new File(resolved)
		if (!file.exists()) {
			println("File does not exist: " + resolved)
			return
		}

		FileInputStream fis = null
		XSSFWorkbook workbook = null
		FileOutputStream fos = null
		try {
			fis = new FileInputStream(file)
			workbook = new XSSFWorkbook(fis)
			Sheet sheet = workbook.getSheet(sheetName)
			if (sheet == null) {
				println("Sheet not found: " + sheetName)
				return
			}

			boolean found = false
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i)
				if (row == null) continue

					// Search in columns 0 (ID), 2 (Tên chức năng), 3 (Chức năng con)
					for (int colIndex : [0, 2, 3]) {
						String val = getCellAsString(row.getCell(colIndex))
						if (val != null && val.trim().equalsIgnoreCase(searchKeyword.trim())) {
							Cell actualCell = row.getCell(7)
							if (actualCell == null) actualCell = row.createCell(7)
							actualCell.setCellValue(actualResult)

							Cell statusCell = row.getCell(8)
							if (statusCell == null) statusCell = row.createCell(8)
							statusCell.setCellValue(status)

							found = true
							break
						}
					}
				if (found) break
			}

			fis.close()
			fos = new FileOutputStream(file)
			workbook.write(fos)
		} catch (Exception e) {
			println("Error updating test result: " + e.getMessage())
		} finally {
			if (fos != null) try {
				fos.close()
			} catch (Exception ignore) {}
			if (fis != null) try {
				fis.close()
			} catch (Exception ignore) {}
			if (workbook != null) try {
				workbook.close()
			} catch (Exception ignore) {}
		}
	}
}
