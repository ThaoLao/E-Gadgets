import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.kms.katalon.core.model.FailureHandling

import utils.dataFileUtil
import utils.openShopPage

/** Kết hợp danh mục + từ khóa (category_combo_label, keyword_combo). */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

String catLabel = util.ensureKeyWithDefault(excelPath, 'category_combo_label', 'Nồi cơm điện', sheetName)
String kw = util.ensureKeyWithDefault(excelPath, 'keyword_combo', 'Sunhouse', sheetName)

new openShopPage().navigateToShop()

TestObject catRadio = new TestObject('shop_category_radio_combo')
catRadio.addProperty('xpath', ConditionType.EQUALS,
		"//input[@type='radio'][following-sibling::span[contains(.,'" + catLabel + "')]]")

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(catRadio, 15)
WebUI.click(catRadio)
WebUI.clearText(findTestObject(SP + 'shop_input_keyword'))
WebUI.setText(findTestObject(SP + 'shop_input_keyword'), kw)
WebUI.click(findTestObject(SP + 'shop_btn_search'))
WebUI.waitForPageLoad(25)

WebUI.verifyElementVisible(findTestObject(SP + 'shop_pagination_status'))
if (WebUI.verifyElementPresent(findTestObject(SP + 'shop_product_title_first'), 5, FailureHandling.OPTIONAL)) {
	String title = WebUI.getText(findTestObject(SP + 'shop_product_title_first'))
	WebUI.verifyEqual(title.toLowerCase().contains(kw.toLowerCase()), true)
}
WebUI.comment('PS05 OK — kết hợp lọc')

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Kết hợp nhiều bộ lọc', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
