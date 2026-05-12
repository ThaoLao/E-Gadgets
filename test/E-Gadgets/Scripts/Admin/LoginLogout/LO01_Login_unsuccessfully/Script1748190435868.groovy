import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ObjectRepository as OR
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable
import utils.generalFunction
import utils.openLoginPage

	'1. Để trống cả email và mật khẩu'
	new openLoginPage().navigateToLogin()
	WebUI.click(findTestObject('User/Auth/btn_login'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/Auth/msg_error_login'))
	
	'2. Để trống email'
	WebUI.setText(findTestObject('User/Auth/Password'), GlobalVariable.General_Password)
	WebUI.click(findTestObject('User/Auth/btn_login'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/Auth/msg_error_login'))

	
	'3. Để trống mật khẩu'
	WebUI.setText(findTestObject('User/Auth/Email'), GlobalVariable.Admin_Email)
	WebUI.click(findTestObject('User/Auth/btn_login'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/Auth/msg_error_login'))

	
	'4. Sai email'
	WebUI.setText(findTestObject('User/Auth/Email'), "sai_email@example.com")
	WebUI.setText(findTestObject('User/Auth/Password'), GlobalVariable.General_Password)
	WebUI.click(findTestObject('User/Auth/btn_login'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/Auth/msg_error_login'))

	
	'5. Sai mật khẩu'
	WebUI.setText(findTestObject('User/Auth/Email'), GlobalVariable.Admin_Email)
	WebUI.setText(findTestObject('User/Auth/Password'), "sai_mat_khau")
	WebUI.click(findTestObject('User/Auth/btn_login'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/Auth/msg_error_login'))
	
	'6. Sai cả email và mật khẩu'
	WebUI.setText(findTestObject('User/Auth/Email'), "sai_email@example.com")
	WebUI.setText(findTestObject('User/Auth/Password'), "sai_mat_khau")
	WebUI.click(findTestObject('User/Auth/btn_login'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/Auth/msg_error_login'))
	WebUI.closeBrowser()
