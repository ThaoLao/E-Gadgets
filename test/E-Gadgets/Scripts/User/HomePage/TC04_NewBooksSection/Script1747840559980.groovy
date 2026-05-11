import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import utils.openPage

	new openPage().navigateToHomePage()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Section_NewBooks'), 20)
	WebUI.scrollToElement(findTestObject('Object Repository/User/HomePage/Section_NewBooks'), 5)
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Section_NewBooks'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Text_ProductTitle'))
