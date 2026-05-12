import utils.dataFileUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

dataFileUtil util = new dataFileUtil()
TestObject makeTO(String xpath) { TestObject to = new TestObject(); to.addProperty('xpath', ConditionType.EQUALS, xpath); return to; }

WebUI.navigateToUrl(GlobalVariable.URL + '/contact')
WebUI.click(makeTO("//button[contains(normalize-space(.),'Gửi')]"))

// Check if HTML5 validation prevents form submit
String validationMessage = WebUI.getAttribute(makeTO("//input[@id='fullname']"), "validationMessage")
WebUI.verifyNotEqual(validationMessage, "")

try { util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Liên hệ', 'Để trống trường bắt buộc', 'Pass', 'Pass') } catch (Exception e) {}
