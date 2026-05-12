import utils.dataFileUtil
import utils.openShopPage
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

new openShopPage().navigateToShop()

String initCount = WebUI.getText(makeTO("//span[@id='cart-quantity']"))
WebUI.click(makeTO("(//div[contains(@class,'product__thumb')]/a)[1]"))
WebUI.click(makeTO("//button[contains(normalize-space(.),'Thêm vào giỏ hàng')]"))
WebUI.waitForAlert(5)
String alertText = WebUI.getAlertText()
WebUI.verifyEqual(alertText.contains('Thêm sản phẩm vào giỏ hàng thành công'), true)
WebUI.acceptAlert()
WebUI.delay(2)

String newCount = WebUI.getText(makeTO("//span[@id='cart-quantity']"))
WebUI.verifyNotEqual(newCount, initCount)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Xem chi tiết', 'Click vào button', 'Pass', 'Pass')
} catch (Exception e) {}