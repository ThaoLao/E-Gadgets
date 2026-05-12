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

// Make sure we have item in cart
WebUI.navigateToUrl(GlobalVariable.URL + '/shop')
WebUI.click(makeTO("(//div[contains(@class,'product__thumb')]/a)[1]"))
WebUI.click(makeTO("//button[contains(normalize-space(.),'Thêm vào giỏ hàng')]"))
WebUI.waitForAlert(5)
WebUI.acceptAlert()

WebUI.navigateToUrl(GlobalVariable.URL + '/cart')

String oldBadge = WebUI.getText(makeTO("//span[@id='cart-quantity']"))
String oldTotal = WebUI.getText(makeTO("(//div[@class='cart__total__amount']/span)[2]"))

WebUI.click(makeTO("(//td[@class='product-remove']/a)[1]"))
WebUI.delay(3)

String newBadge = WebUI.getText(makeTO("//span[@id='cart-quantity']"))
String newTotal = WebUI.getText(makeTO("(//div[@class='cart__total__amount']/span)[2]"))

WebUI.verifyNotEqual(newBadge, oldBadge)
WebUI.verifyNotEqual(newTotal, oldTotal)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý giỏ hàng', 'Bỏ sản phẩm khỏi giỏ', 'Pass', 'Pass')
} catch (Exception e) {}
