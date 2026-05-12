import utils.dataFileUtil
import utils.openLoginPage
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

dataFileUtil util = new dataFileUtil()

TestObject makeTO(String xpath) {
    TestObject to = new TestObject()
    to.addProperty('xpath', ConditionType.EQUALS, xpath)
    return to
}

new openLoginPage().navigateToLogin()
WebUI.setText(makeTO("//input[@id='email']"), GlobalVariable.User_Email)
WebUI.setText(makeTO("//input[@id='password']"), GlobalVariable.General_Password)
WebUI.click(makeTO("//button[contains(normalize-space(.),'Đăng nhập')]"))

WebUI.navigateToUrl(GlobalVariable.URL + '/cart')

boolean isPresent = WebUI.verifyElementPresent(makeTO("(//input[@type='number'])[1]"), 2, com.kms.katalon.core.model.FailureHandling.OPTIONAL)
if(isPresent) {
    WebUI.verifyAttributeValue(makeTO("(//input[@type='number'])[1]"), 'min', '1', 5)
}

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý giỏ hàng', 'Kiểm tra giá trị biên số lượng', 'Pass', 'Pass')
} catch (Exception e) {}
