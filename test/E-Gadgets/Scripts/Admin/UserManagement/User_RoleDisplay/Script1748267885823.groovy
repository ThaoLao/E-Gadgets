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
WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/UserManagement/Button_User'), 3)
WebUI.click(findTestObject('Object Repository/Admin/UserManagement/Button_User'))
WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/UserManagement/Button_User_Management'), 3)
WebUI.click(findTestObject('Object Repository/Admin/UserManagement/Button_User_Management'))
WebUI.delay(3)

// Verify role badges exist
WebUI.verifyElementPresent(makeTO("//span[contains(@class,'role-badge')]"), 5)
// Verify ADMIN or USER badges
boolean hasAdmin = WebUI.verifyElementPresent(makeTO("//span[contains(@class,'role-admin') and contains(normalize-space(.),'ADMIN')]"), 3, FailureHandling.OPTIONAL)
boolean hasUser = WebUI.verifyElementPresent(makeTO("//span[contains(@class,'role-user') and contains(normalize-space(.),'USER')]"), 3, FailureHandling.OPTIONAL)
WebUI.verifyEqual(hasAdmin || hasUser, true)
SessionHelper.endLoggedInSessionCleanly()
try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý người dùng', 'Hiển thị vai trò (Role)', 'Pass', 'Pass')
} catch (Exception e) {}
