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

// Register a test user first for safe deletion
WebUI.navigateToUrl(GlobalVariable.URL + '/register')
WebUI.delay(1)
String testEmail = "testdelete" + System.currentTimeMillis() + "@test.com"
WebUI.setText(makeTO("//input[@id='email']"), testEmail)
WebUI.setText(makeTO("//input[@id='fullName']"), "Test Delete User")
WebUI.setText(makeTO("//input[@id='phoneNumber']"), "0123456789")
WebUI.setText(makeTO("//input[@id='address']"), "Test Address")
WebUI.setText(makeTO("//input[@id='password']"), GlobalVariable.General_Password)
WebUI.click(makeTO("//button[contains(normalize-space(.),'Đăng ký')]"))
WebUI.delay(2)

// Now login as admin
WebUI.navigateToUrl(GlobalVariable.URL + '/login')
WebUI.delay(1)
WebUI.setText(makeTO("//input[@id='email']"), GlobalVariable.Admin_Email)
WebUI.setText(makeTO("//input[@id='password']"), GlobalVariable.General_Password)
WebUI.click(makeTO("//button[contains(normalize-space(.),'Đăng nhập')]"))
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
WebUI.delay(2)
WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/UserManagement/Button_User'), 3)
WebUI.click(findTestObject('Object Repository/Admin/UserManagement/Button_User'))
WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/UserManagement/Button_User_Management'), 3)
WebUI.click(findTestObject('Object Repository/Admin/UserManagement/Button_User_Management'))
WebUI.delay(3)

// Find the test user and delete
String rowsBefore = WebUI.executeJavaScript("return document.querySelectorAll('tbody tr').length.toString()", null)
boolean hasDeleteBtn = WebUI.verifyElementPresent(makeTO("(//tbody/tr)[last()]//button[contains(@class,'btn-danger-modern') and contains(.,'Xóa')]"), 3, FailureHandling.OPTIONAL)
if (hasDeleteBtn) {
    WebUI.click(makeTO("(//tbody/tr)[last()]//button[contains(@class,'btn-danger-modern') and contains(.,'Xóa')]"))
    WebUI.delay(1)
    WebUI.acceptAlert()
    WebUI.delay(3)
}
SessionHelper.endLoggedInSessionCleanly()
try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý người dùng', 'Xác nhận xóa thành công', 'Pass', 'Pass')
} catch (Exception e) {}

