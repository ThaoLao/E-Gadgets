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
WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/Admin_Category/Button_Category_Management'), 3)
WebUI.click(findTestObject('Object Repository/Admin/Admin_Category/Button_Category_Management'))
WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/Admin_Category/Button_Category_List'), 3)
WebUI.click(findTestObject('Object Repository/Admin/Admin_Category/Button_Category_List'))
WebUI.delay(3)

// Go to last page first to delete test data
WebUI.click(makeTO("//a[contains(normalize-space(.),'Trang cuối')]"))
WebUI.delay(2)

// Count rows before
String rowsBefore = WebUI.executeJavaScript("return document.querySelectorAll('tbody tr').length.toString()", null)

// Click delete button on last row
WebUI.click(makeTO("(//tbody/tr)[last()]//button[contains(@class,'btn-delete')]"))
WebUI.waitForElementVisible(makeTO("//div[@id='deleteConfirmationModal']"), 5)

// First test: Cancel
WebUI.verifyElementVisible(makeTO("//button[contains(normalize-space(.),'Hủy')]"))
WebUI.click(makeTO("//button[contains(normalize-space(.),'Hủy')]"))
WebUI.delay(1)

// Verify row still exists
String rowsAfterCancel = WebUI.executeJavaScript("return document.querySelectorAll('tbody tr').length.toString()", null)
WebUI.verifyEqual(rowsBefore, rowsAfterCancel)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý danh mục', 'Xác nhận xóa (Hủy)', 'Pass', 'Pass')
} catch (Exception e) {}


// Now actually delete
WebUI.click(makeTO("(//tbody/tr)[last()]//button[contains(@class,'btn-delete')]"))
WebUI.waitForElementVisible(makeTO("//div[@id='deleteConfirmationModal']"), 5)
WebUI.click(makeTO("//div[@id='deleteConfirmationModal']//a[@id='deleteButton']"))
WebUI.delay(3)

SessionHelper.endLoggedInSessionCleanly()

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý danh mục', 'Xác nhận xóa (Đồng ý)', 'Pass', 'Pass')
} catch (Exception e) {}


