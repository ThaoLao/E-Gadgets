import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.dataFileUtil
import utils.openShopPage

/** Trim khoảng trắng thừa — keyword_trim_input (vd: "  12  "). */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

util.setData(excelPath, 'keyword_trim_input', '  Panasonic  ', sheetName)
String raw = util.ensureKeyWithDefault(excelPath, 'keyword_trim_input', '  Panasonic  ', sheetName)

new openShopPage().navigateToShop()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_input_keyword'), 20)
WebUI.clearText(findTestObject(SP + 'shop_input_keyword'))
WebUI.setText(findTestObject(SP + 'shop_input_keyword'), raw)
WebUI.click(findTestObject(SP + 'shop_btn_search'))
WebUI.waitForPageLoad(25)

WebUI.verifyElementVisible(findTestObject(SP + 'shop_product_title_first'))
String trimmed = raw.trim()
String title = WebUI.getText(findTestObject(SP + 'shop_product_title_first'))
WebUI.verifyEqual(title.toLowerCase().contains(trimmed.toLowerCase()), true)
WebUI.comment('PS02 OK — trim keyword')

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Kiểm tra Trimspace', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
