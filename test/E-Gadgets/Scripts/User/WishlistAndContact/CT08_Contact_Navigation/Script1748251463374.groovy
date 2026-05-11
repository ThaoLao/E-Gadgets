import utils.dataFileUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

dataFileUtil util = new dataFileUtil()
TestObject makeTO(String xpath) { TestObject to = new TestObject(); to.addProperty('xpath', ConditionType.EQUALS, xpath); return to; }

WebUI.navigateToUrl(GlobalVariable.URL + '/contact')

WebUI.click(makeTO("//a[text()='Trang chủ']"))
WebUI.verifyMatch(WebUI.getUrl(), ".*", true)

WebUI.navigateToUrl(GlobalVariable.URL + '/contact')
WebUI.click(makeTO("//a[text()='Cửa hàng']"))
WebUI.verifyMatch(WebUI.getUrl(), ".*shop.*", true)

try { util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Liên hệ', 'Chuyển trang liên kết', 'Pass', 'Pass') } catch (Exception e) {}
