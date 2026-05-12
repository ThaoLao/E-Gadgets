import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ObjectRepository as OR
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable
import utils.*

	new openLoginPage().navigateToLogin()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/Email'), 15)
	WebUI.setText(findTestObject('Object Repository/User/Auth/Email'), GlobalVariable.Admin_Email)
	WebUI.setText(findTestObject('Object Repository/User/Auth/Password'), String.valueOf(GlobalVariable.General_Password))
	WebUI.click(findTestObject('Object Repository/User/Auth/btn_login'))
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/icon_Accounts'), 20)



