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

// Click Tam dung button on first active product
WebUI.click(makeTO("(//tbody/tr)[1]//button[contains(.,'Tạm dừng')]"))
WebUI.delay(1)

// Verify popup displays
WebUI.verifyElementVisible(makeTO("//div[@id='deleteConfirmationModal']"))
WebUI.verifyElementVisible(makeTO("//p[contains(normalize-space(.),'Bạn có chắc muốn tạm dừng bán sản phẩm này?')]"))
WebUI.verifyElementVisible(makeTO("//button[contains(normalize-space(.),'Hủy')]"))
WebUI.verifyElementVisible(makeTO("//div[@id='deleteConfirmationModal']//a[@id='deleteButton']"))

// Cancel
WebUI.click(makeTO("//div[@id='deleteConfirmationModal']//button[contains(normalize-space(.),'Hủy')]"))
WebUI.delay(1)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý sản phẩm', 'Hiển thị popup xác nhận', 'Pass', 'Pass')
} catch (Exception e) {}

SessionHelper.endLoggedInSessionCleanly()


