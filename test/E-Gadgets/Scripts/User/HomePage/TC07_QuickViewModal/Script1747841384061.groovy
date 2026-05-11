import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import utils.openPage

	new openPage().navigateToHomePage()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Button_QuickView'), 20)
	WebUI.scrollToElement(findTestObject('Object Repository/User/HomePage/Button_QuickView'), 5)
	WebUI.click(findTestObject('Object Repository/User/HomePage/Button_QuickView'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Modal_QuickView'), 10)
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Modal_QuickView'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Modal_BookTitle'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Modal_Button_AddToCart'))