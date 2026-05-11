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

WebUI.verifyElementVisible(makeTO("//th[contains(text(),'ID')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Tên đăng nhập')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Họ và tên')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Số điện thoại')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Địa chỉ')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Vai trò')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Hành động')]"))
SessionHelper.endLoggedInSessionCleanly()
try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý người dùng', 'Hiển thị dữ liệu', 'Pass', 'Pass')
} catch (Exception e) {}
