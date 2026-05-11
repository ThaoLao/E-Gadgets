import utils.dataFileUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

dataFileUtil util = new dataFileUtil()
TestObject makeTO(String xpath) { TestObject to = new TestObject(); to.addProperty('xpath', ConditionType.EQUALS, xpath); return to; }

WebUI.navigateToUrl(GlobalVariable.URL + '/contact')

String longText = "A" * 550
WebUI.setText(makeTO("//textarea[@id='message']"), longText)
String actualText = WebUI.getAttribute(makeTO("//textarea[@id='message']"), "value")

// Since maxlength is 500, it should be cut
WebUI.verifyEqual(actualText.length(), 500)

try { util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Liên hệ', 'Giới hạn ký tự', 'Pass', 'Pass') } catch (Exception e) {}
