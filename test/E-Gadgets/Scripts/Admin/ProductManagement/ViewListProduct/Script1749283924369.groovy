import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.WebElement
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import utils.dataFileUtil
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

WebUI.verifyElementVisible(makeTO("//th[contains(text(),'ID')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Tên sản phẩm')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Người nhập hàng')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Nhà phân phối')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Ngày nhập kho')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Giá gốc')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Giá sale')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Số lượng')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Khối lượng')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Ảnh bìa')]"))
WebUI.verifyElementVisible(makeTO("//th[contains(text(),'Hành động')]"))
SessionHelper.endLoggedInSessionCleanly()
try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý sản phẩm', 'Hiển thị dữ liệu', 'Pass', 'Pass')
} catch (Exception e) {}
