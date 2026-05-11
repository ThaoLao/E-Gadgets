import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.dataFileUtil
import utils.openShopPage

/** Lọc theo danh mục — chọn radio theo nhãn category_label, bấm Tìm kiếm. */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

String label = util.ensureKeyWithDefault(excelPath, 'category_label', 'Máy hút bụi', sheetName)

new openShopPage().navigateToShop()

TestObject catRadio = new TestObject('shop_category_radio')
catRadio.addProperty('xpath', ConditionType.EQUALS,
		"//input[@type='radio'][following-sibling::span[contains(.,'" + label + "')]]")

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(catRadio, 15)
WebUI.click(catRadio)
WebUI.click(findTestObject(SP + 'shop_btn_search'))
WebUI.waitForPageLoad(25)

WebUI.verifyElementVisible(findTestObject(SP + 'shop_pagination_status'))
String heading = WebUI.getText(findTestObject(SP + 'shop_category_heading'))
WebUI.verifyEqual(heading.contains(label), true)
WebUI.comment('PS04 OK — danh mục: ' + label)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Lọc theo danh mục', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
