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

String jsTotal = "let subtotals = document.querySelectorAll('.product-subtotal');" +
    "let sum = 0;" +
    "subtotals.forEach(function(s) {" +
    "  let val = s.innerText.replace('VND','').replace(/\\./g,'').trim();" +
    "  sum += parseInt(val);" +
    "});" +
    "return sum.toString();"
String sumStr = WebUI.executeJavaScript(jsTotal, null)
String uiTotalRaw = WebUI.getText(makeTO("(//div[@class='cart__total__amount']/span)[2]"))
String uiTotal = uiTotalRaw.replace('VND','').replace('.','').trim()

WebUI.verifyEqual(sumStr, uiTotal)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Quản lý giỏ hàng', 'Tính toán tổng đơn hàng', 'Pass', 'Pass')
} catch (Exception e) {}
