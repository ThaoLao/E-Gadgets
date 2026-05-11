import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import java.util.Date

import utils.dataFileUtil
import utils.openShopPage

/**
 * Tìm kiếm theo từ khóa (sheet Tim_Kiem — keyword_match).
 * Kỳ vọng: có ít nhất một sản phẩm khớp (tiêu đề chứa từ khóa sau khi trim).
 */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

util.setData(excelPath, 'keyword_match', 'Panasonic', sheetName)
String kw = util.ensureKeyWithDefault(excelPath, 'keyword_match', 'Panasonic', sheetName)

new openShopPage().navigateToShop()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_input_keyword'), 20)
WebUI.clearText(findTestObject(SP + 'shop_input_keyword'))
WebUI.setText(findTestObject(SP + 'shop_input_keyword'), kw)
WebUI.click(findTestObject(SP + 'shop_btn_search'))
WebUI.waitForPageLoad(25)

WebUI.verifyElementNotPresent(findTestObject(SP + 'shop_msg_no_products'), 5)
WebUI.verifyElementVisible(findTestObject(SP + 'shop_product_title_first'))
String title = WebUI.getText(findTestObject(SP + 'shop_product_title_first'))
WebUI.verifyEqual(title.toLowerCase().contains(kw.trim().toLowerCase()), true)

String ts = new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(new Date())
util.setData(excelPath, 'last_search_ok_at', ts, sheetName)
WebUI.comment('PS01 OK — keyword: ' + kw + ' — ghi last_search_ok_at: ' + ts)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Tìm kiếm theo từ khóa', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
	e.printStackTrace()
}
