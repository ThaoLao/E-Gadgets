import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import utils.openPage

	new openPage().navigateToHomePage()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Button_ShopNow'), 20)
	WebUI.scrollToElement(findTestObject('Object Repository/User/HomePage/Button_ShopNow'), 5)
	WebUI.waitForElementClickable(findTestObject('Object Repository/User/HomePage/Button_ShopNow'), 10)
	WebUI.click(findTestObject('Object Repository/User/HomePage/Button_ShopNow'))
	WebUI.waitForPageLoad(10)
	WebUI.verifyMatch(WebUI.getUrl(), '.*shop.*', true)