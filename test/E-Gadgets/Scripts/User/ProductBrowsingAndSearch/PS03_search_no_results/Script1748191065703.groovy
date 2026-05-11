import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.dataFileUtil
import utils.openShopPage

/** Không có kết quả — hiển thị "Không tìm thấy sản phẩm nào", trạng thái 0/0. */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

String kw = util.ensureKeyWithDefault(excelPath, 'keyword_no_result', 'zzznoresult999', sheetName)

new openShopPage().navigateToShop()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_input_keyword'), 20)
WebUI.clearText(findTestObject(SP + 'shop_input_keyword'))
WebUI.setText(findTestObject(SP + 'shop_input_keyword'), kw)
WebUI.click(findTestObject(SP + 'shop_btn_search'))
WebUI.waitForPageLoad(25)

WebUI.verifyElementVisible(findTestObject(SP + 'shop_msg_no_products'))
WebUI.verifyElementText(findTestObject(SP + 'shop_msg_no_products'), 'Không tìm thấy sản phẩm nào')
String status = WebUI.getText(findTestObject(SP + 'shop_pagination_status'))
WebUI.verifyEqual(status.contains('0 of 0'), true)
WebUI.comment('PS03 OK — không có sản phẩm')

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Tìm kiếm không có kết quả', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
