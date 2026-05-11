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
WebUI.click(makeTO("//button[contains(text(),'Đăng nhập')]"))

new openShopPage().navigateToShop()

WebUI.click(makeTO("(//div[contains(@class,'product__thumb')]/a)[1]"))
WebUI.clearText(makeTO("//input[@id='qty']"))
WebUI.setText(makeTO("//input[@id='qty']"), '3')
WebUI.verifyElementAttributeValue(makeTO("//input[@id='qty']"), 'value', '3', 10)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Xem chi tiết', 'Nhập số lượng', 'Pass', 'Pass')
} catch (Exception e) {}