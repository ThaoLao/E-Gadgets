package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.apache.poi.ss.usermodel.Cell

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import org.apache.poi.xssf.usermodel.XSSFWorkbook

import internal.GlobalVariable

import org.apache.poi.ss.usermodel.*

public class registerDataFileUtil {

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
			// Remove trailing .0 for whole numbers
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
			default:
				return null
		}
	}

	@Keyword
	def setData(String filePath, String key, def value) {
		String valueToWrite = ""

		if (value == null) {
			valueToWrite = ""
		} else if (value instanceof String) {
			valueToWrite = value
		} else if (value.hasProperty("getText")) {
			try {
				valueToWrite = value.getText()
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
				sheet = workbook.getSheet('Sheet1')
				if (sheet == null) {
					sheet = workbook.createSheet('Sheet1')
				}
			} else {
				workbook = new XSSFWorkbook()
				sheet = workbook.createSheet('Sheet1')
				// Create header row
				Row header = sheet.createRow(0)
				header.createCell(0).setCellValue('Key')
				header.createCell(1).setCellValue('Value')
			}
		} catch (Exception e) {
			// If file is corrupted, create new one
			if (workbook != null) try {
				workbook.close()
			} catch (Exception ignore) {}
			workbook = new XSSFWorkbook()
			sheet = workbook.createSheet('Sheet1')
			Row header = sheet.createRow(0)
			header.createCell(0).setCellValue('Key')
			header.createCell(1).setCellValue('Value')
		} finally {
			if (fis != null) try {
				fis.close()
			} catch (Exception ignore) {}
		}

		boolean found = false
		int startRow = 0
		// Skip header row if it exists
		if (sheet.getRow(0) != null && "Key".equals(getCellAsString(sheet.getRow(0).getCell(0)))) {
			startRow = 1
		}

		for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i)
			if (row != null) {
				String cellKey = getCellAsString(row.getCell(0))
				if (cellKey != null && cellKey.equals(key)) {
					Cell valueCell = row.getCell(1)
					if (valueCell == null) valueCell = row.createCell(1)
					valueCell.setCellValue(valueToWrite)
					found = true
					break
				}
			}
		}

		if (!found) {
			int newRowIndex = sheet.getLastRowNum() + 1
			Row newRow = sheet.createRow(newRowIndex)
			newRow.createCell(0).setCellValue(key)
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
	def String getData(String filePath, String key) {
		String resolved = resolvePath(filePath)
		File file = new File(resolved)
		if (!file.exists()) return null

		FileInputStream fis = null
		XSSFWorkbook workbook = null
		try {
			fis = new FileInputStream(file)
			workbook = new XSSFWorkbook(fis)
			Sheet sheet = workbook.getSheet('Sheet1')
			String result = null
			if (sheet != null) {
				int startRow = 0
				// Skip header row if it exists
				if (sheet.getRow(0) != null && "Key".equals(getCellAsString(sheet.getRow(0).getCell(0)))) {
					startRow = 1
				}

				for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i)
					if (row != null) {
						String cellKey = getCellAsString(row.getCell(0))
						if (cellKey != null && cellKey.equals(key)) {
							result = getCellAsString(row.getCell(1))
							break
						}
					}
				}
			}
			return result
		} catch (Exception e) {
			// If file is corrupted or can't be read, return null
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

	@Keyword
	def initializeDefaultData(String filePath) {
		String resolved = resolvePath(filePath)
		File file = new File(resolved)

		// Create default data if file doesn't exist
		if (!file.exists()) {
			// User registration data
			setData(filePath, 'register_email', 'testuser@example.com')
			setData(filePath, 'register_password', 'password123')
			setData(filePath, 'register_name', 'Test User')
			setData(filePath, 'register_phone', '0123456789')
			setData(filePath, 'register_address', '123 Test Street, Test City')

			// Product data
			setData(filePath, 'AddToCard_Tittle1', 'The Family Experiment')
			setData(filePath, 'AddToCard_Price1', '100000')
			setData(filePath, 'AddToCard_Quantity1', '2')

			setData(filePath, 'AddToCard_Tittle2', 'Product Title 2')
			setData(filePath, 'AddToCard_Price2', '150000')
			setData(filePath, 'AddToCard_Quantity2', '1')

			// Order data
			setData(filePath, 'Order_Code', 'ORDER001')
			setData(filePath, 'Order_Date', '2025-01-01')
			setData(filePath, 'Order_Total', '200000')
			setData(filePath, 'Order_Payment', 'COD')
			setData(filePath, 'Order_Status', 'PENDING')

			setData(filePath, 'OrderVNPay_Code', 'ORDER002')
			setData(filePath, 'OrderVNPay_Date', '2025-01-01')
			setData(filePath, 'OrderVNPay_Total', '200000')
			setData(filePath, 'OrderVNPay_Payment', 'VNPAY')
			setData(filePath, 'OrderVNPay_Status', 'PENDING')
		}
	}
}
