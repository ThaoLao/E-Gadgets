import utils.dataFileUtil
import utils.openLoginPage
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable
import org.openqa.selenium.Keys

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

// Make sure we have item in cart
WebUI.navigateToUrl(GlobalVariable.URL + '/shop')
WebUI.click(makeTO("(//div[contains(@class,'product__thumb')]/a)[1]"))
WebUI.click(makeTO("//button[contains(text(),'Thêm vào giỏ hàng')]"))
WebUI.waitForAlert(5)
WebUI.acceptAlert()

WebUI.navigateToUrl(GlobalVariable.URL + '/cart')
String oldSubtotal = WebUI.getText(makeTO("(//td[@class='product-subtotal'])[1]"))
String oldTotal = WebUI.getText(makeTO("(//div[@class='cart__total__amount']/span)[2]"))

WebUI.click(makeTO("(//input[@type='number'])[1]"))
WebUI.sendKeys(makeTO("(//input[@type='number'])[1]"), Keys.chord(Keys.ARROW_UP, Keys.ENTER))
// Trigger change or wait
WebUI.delay(5)

String newSubtotal = WebUI.getText(makeTO("(//td[@class='product-subtotal'])[1]"))
String newTotal = WebUI.getText(makeTO("(//div[@class='cart__total__amount']/span)[2]"))

WebUI.verifyNotEqual(newSubtotal, oldSubtotal)
WebUI.verifyNotEqual(newTotal, oldTotal)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý giỏ hàng', 'Thay đổi số lượng sản phẩm', 'Pass', 'Pass')
} catch (Exception e) {}
