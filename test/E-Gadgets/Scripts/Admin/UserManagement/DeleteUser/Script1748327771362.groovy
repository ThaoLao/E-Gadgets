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
SessionHelper.endLoggedInSessionCleanly()
// Find a non-admin user delete button and click
boolean hasDeleteBtn = WebUI.verifyElementPresent(makeTO("(//tbody/tr)[1]//button[contains(@class,'btn-danger-modern') and contains(.,'Xóa')]"), 3, FailureHandling.OPTIONAL)
if (hasDeleteBtn) {
    WebUI.click(makeTO("(//tbody/tr)[1]//button[contains(@class,'btn-danger-modern') and contains(.,'Xóa')]"))
    WebUI.delay(1)
    // Verify confirm dialog appears
    String alertText = WebUI.getAlertText()
    WebUI.verifyMatch(alertText, '.*Bạn có chắc chắn muốn xóa người dùng này không.*', true)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý người dùng', 'Hiển thị popup xác nhận', 'Pass', 'Pass')
} catch (Exception e) {}

    // Dismiss (cancel)
    WebUI.dismissAlert()
    WebUI.delay(1)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý người dùng', 'Hủy thao tác xóa', 'Pass', 'Pass')
} catch (Exception e) {}

}

