import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import utils.*

	WebUI.callTestCase(
		findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
		[('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	
	generalFunction fc = new generalFunction()
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	
	TestObject btnReplyList = findTestObject('Object Repository/Admin/ContactManagement/List_Button_Reply') 
	List<WebElement> replyBtns = WebUiCommonHelper.findWebElements(btnReplyList, 10)
	
	if (replyBtns.size() > 0) {
		WebElement replyBtn = replyBtns.get(0)
		WebUI.executeJavaScript("arguments[0].scrollIntoView(true);", Arrays.asList(replyBtn))
		WebUI.delay(1)
		WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(replyBtn))
		
		WebUI.waitForElementVisible(findTestObject('Object Repository/Admin/ContactManagement/Response_Title'), 5)
		WebUI.setText(findTestObject('Object Repository/Admin/ContactManagement/Input_Response_Title'), 'Cảm ơn bạn đã liên hệ!')
		
		WebUI.setText(findTestObject('Object Repository/Admin/ContactManagement/Input_Response_Content'), 'Chúng tôi đã nhận được phản ánh và sẽ phản hồi sớm nhất có thể.')
		
		WebUI.click(findTestObject('Object Repository/Admin/ContactManagement/Button_Submit_Response'))
		WebUI.delay(2)
		WebUI.verifyElementText(findTestObject('Object Repository/Admin/ContactManagement/Msg_Contact'), "Gửi mail thành công!")		
		WebUI.delay(2)
		WebUI.back()
		WebUI.delay(2)
	} else {
		WebUI.comment("Không tìm thấy nút 'Phản hồi'")
	}
	

	
	SessionHelper.endLoggedInSessionCleanly()
	