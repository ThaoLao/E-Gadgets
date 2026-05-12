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

WebUI.navigateToUrl(GlobalVariable.URL + '/shop')
WebUI.click(makeTO("(//div[contains(@class,'product__thumb')]/a)[1]"))
WebUI.click(makeTO("//button[contains(normalize-space(.),'Thêm vào giỏ hàng')]"))
WebUI.waitForAlert(5)
WebUI.acceptAlert()

WebUI.navigateToUrl(GlobalVariable.URL + '/cart/checkout')

String name = WebUI.getAttribute(makeTO("//input[@id='fullName']"), 'value')
String phone = WebUI.getAttribute(makeTO("//input[@id='phoneNumber']"), 'value')
String email = WebUI.getAttribute(makeTO("//input[@id='email']"), 'value')

WebUI.verifyNotEqual(name, '')
WebUI.verifyNotEqual(phone, '')
WebUI.verifyNotEqual(email, '')

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Thanh toán', 'Kiểm tra thông tin mặc định', 'Pass', 'Pass')
} catch (Exception e) {}
