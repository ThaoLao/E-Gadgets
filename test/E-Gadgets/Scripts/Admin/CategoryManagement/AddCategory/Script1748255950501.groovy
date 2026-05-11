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

int index = GlobalVariable.CategoryIndex
String name = "Category" + index
String desc = "This is " + name

WebUI.click(findTestObject('Object Repository/Admin/Admin_Category/Button_Category_Add'))
WebUI.verifyMatch(WebUI.getUrl(), '.*categories_management.*', true)
WebUI.setText(findTestObject('Object Repository/Admin/Admin_Category/Input_Category_Name'), name)
WebUI.setText(findTestObject('Object Repository/Admin/Admin_Category/Input_Category_Description'), desc)
WebUI.click(findTestObject('Object Repository/Admin/Admin_Category/Button_Category_Add_Submit'))
WebUI.delay(2)

WebUI.verifyElementPresent(makeTO("//div[contains(@class,'alert')]//span[contains(text(),'thành công')]"), 5, FailureHandling.OPTIONAL)
GlobalVariable.CategoryIndex = index + 1

SessionHelper.endLoggedInSessionCleanly()

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý danh mục', 'Thêm thành công', 'Pass', 'Pass')
} catch (Exception e) {}
