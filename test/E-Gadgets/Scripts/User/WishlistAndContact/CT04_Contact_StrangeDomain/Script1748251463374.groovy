import utils.dataFileUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

dataFileUtil util = new dataFileUtil()
TestObject makeTO(String xpath) { TestObject to = new TestObject(); to.addProperty('xpath', ConditionType.EQUALS, xpath); return to; }

WebUI.navigateToUrl(GlobalVariable.URL + '/contact')
WebUI.setText(makeTO("//input[@id='fullname']"), "Nguyen Van A")
WebUI.setText(makeTO("//input[@id='email']"), "test@company.international")
WebUI.setText(makeTO("//input[@id='title']"), "Test")
WebUI.setText(makeTO("//textarea[@id='message']"), "Test")
WebUI.click(makeTO("//button[contains(text(),'Gửi')]"))

WebUI.verifyElementVisible(makeTO("//div[contains(text(),'Cảm ơn bạn đã liên hệ')]"))
try { util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Liên hệ', 'Email có tên miền lạ', 'Pass', 'Pass') } catch (Exception e) {}
