import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.kms.katalon.core.model.FailureHandling

import utils.openShopPage

/** Phân trang: nếu có trang 2 thì chuyển; kiểm tra Đầu/Cuối. */
new openShopPage().navigateToShop()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_pagination_status'), 20)

TestObject page2 = new TestObject('shop_page_2')
page2.addProperty('xpath', ConditionType.EQUALS, "//a[contains(@class,'shop-page-link') and normalize-space(text())='2']")

if (WebUI.verifyElementPresent(page2, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(page2)
	WebUI.waitForPageLoad(25)
	WebUI.verifyElementVisible(findTestObject(SP + 'shop_pagination_status'))
	String st = WebUI.getText(findTestObject(SP + 'shop_pagination_status'))
	WebUI.verifyEqual(st.contains('2'), true)
}

if (WebUI.verifyElementPresent(findTestObject(SP + 'shop_page_first'), 3, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject(SP + 'shop_page_first'))
	WebUI.waitForPageLoad(25)
}
if (WebUI.verifyElementPresent(findTestObject(SP + 'shop_page_last'), 3, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject(SP + 'shop_page_last'))
	WebUI.waitForPageLoad(25)
}
WebUI.comment('PS09 OK — phân trang')

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Điều hướng trang', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
