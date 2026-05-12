import utils.dataFileUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

dataFileUtil util = new dataFileUtil()
TestObject makeTO(String xpath) { TestObject to = new TestObject(); to.addProperty('xpath', ConditionType.EQUALS, xpath); return to; }

WebUI.navigateToUrl(GlobalVariable.URL + '/contact')
WebUI.setText(makeTO("//input[@id='fullname']"), "Nguyen Van A")
WebUI.setText(makeTO("//input[@id='email']"), "invalid-email")
WebUI.setText(makeTO("//input[@id='title']"), "Test")
WebUI.setText(makeTO("//textarea[@id='message']"), "Test")
WebUI.click(makeTO("//button[contains(normalize-space(.),'Gửi')]"))

String validationMessage = WebUI.getAttribute(makeTO("//input[@id='email']"), "validationMessage")
WebUI.verifyNotEqual(validationMessage, "")

try { util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Liên hệ', 'Sai định dạng Email', 'Pass', 'Pass') } catch (Exception e) {}
