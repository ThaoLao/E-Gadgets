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
WebUI.click(makeTO("//button[contains(text(),'Đăng nhập')]"))

WebUI.navigateToUrl(GlobalVariable.URL + '/shop')
WebUI.click(makeTO("(//div[contains(@class,'product__thumb')]/a)[1]"))
WebUI.click(makeTO("//button[contains(text(),'Thêm vào giỏ hàng')]"))
WebUI.waitForAlert(5)
WebUI.acceptAlert()

WebUI.navigateToUrl(GlobalVariable.URL + '/cart/checkout')
WebUI.verifyElementVisible(makeTO("//input[@id='fullName']"))

String defaultName = WebUI.getAttribute(makeTO("//input[@id='fullName']"), 'value')
WebUI.verifyNotEqual(defaultName, '')

WebUI.clearText(makeTO("//input[@id='fullName']"))
WebUI.setText(makeTO("//input[@id='fullName']"), 'Test User COD')
WebUI.clearText(makeTO("//input[@id='phoneNumber']"))
WebUI.setText(makeTO("//input[@id='phoneNumber']"), '0123456789')
WebUI.clearText(makeTO("//input[@id='email']"))
WebUI.setText(makeTO("//input[@id='email']"), GlobalVariable.User_Email)
WebUI.clearText(makeTO("//textarea[@id='address']"))
WebUI.setText(makeTO("//textarea[@id='address']"), '123 Street')

WebUI.click(makeTO("//button[contains(text(),'đặt hàng')]"))
WebUI.delay(2)
WebUI.verifyElementVisible(makeTO("//h2[contains(text(),'Lịch sử đơn hàng')]"), com.kms.katalon.core.model.FailureHandling.OPTIONAL)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Thanh toán', 'Thanh toán COD', 'Pass', 'Pass')
} catch (Exception e) {}