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
WebUI.click(makeTO("//a[contains(@href, '/admin/products_management')]"))
WebUI.delay(1)
WebUI.click(makeTO("//a[contains(@href,'/admin/products_management')]"))
WebUI.delay(3)

WebUI.click(makeTO("(//tbody/tr)[1]//a[contains(@class,'btn-primary-modern') and contains(.,'Sửa')]"))
WebUI.delay(2)
WebUI.click(makeTO("//a[contains(normalize-space(.),'QUAY LẠI')] | //a[contains(normalize-space(.),'Quay lại')]"))
WebUI.delay(2)
WebUI.verifyMatch(WebUI.getUrl(), '.*products_management.*', true)

SessionHelper.endLoggedInSessionCleanly()

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý sản phẩm', 'Quay lại trang trước', 'Pass', 'Pass')
} catch (Exception e) {}


