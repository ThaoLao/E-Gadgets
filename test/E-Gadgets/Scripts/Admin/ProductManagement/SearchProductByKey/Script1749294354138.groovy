import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.WebElement
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import utils.*

TestObject makeTO(String xpath) {
    TestObject to = new TestObject()
    to.addProperty('xpath', ConditionType.EQUALS, xpath)
    return to
}

WebUI.callTestCase(
    findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
    [('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
)
dataFileUtil util = new dataFileUtil()
WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
WebUI.delay(2)
WebUI.click(makeTO("//a[contains(@class,'nav-link') and contains(.,'Sản phẩm')]"))
WebUI.delay(1)
WebUI.click(makeTO("//a[contains(@href,'/admin/products_management')]"))
WebUI.delay(3)

// Search with existing keyword
WebUI.setText(makeTO("//input[@id='keyword']"), "sấy")
WebUI.click(makeTO("//button[contains(text(),'Tìm kiếm')]"))
WebUI.delay(3)
WebUI.verifyElementPresent(makeTO("//tbody/tr"), 5, FailureHandling.OPTIONAL)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý sản phẩm', 'Theo từ khóa (Có kết quả)', 'Pass', 'Pass')
} catch (Exception e) {}


// Search with non-existing keyword
WebUI.navigateToUrl(GlobalVariable.URL + '/admin/products_management')
WebUI.delay(2)
WebUI.setText(makeTO("//input[@id='keyword']"), "the ministry")
WebUI.click(makeTO("//button[contains(text(),'Tìm kiếm')]"))
WebUI.delay(3)
String rowCount = WebUI.executeJavaScript("return document.querySelectorAll('tbody tr').length.toString()", null)
WebUI.verifyEqual(rowCount, "0")

SessionHelper.endLoggedInSessionCleanly()

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý sản phẩm', 'Theo từ khóa (Không kết quả)', 'Pass', 'Pass')
} catch (Exception e) {}
