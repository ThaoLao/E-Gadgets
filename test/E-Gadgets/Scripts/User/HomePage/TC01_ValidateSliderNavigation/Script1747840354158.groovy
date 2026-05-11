import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import utils.openPage

	new openPage().navigateToHomePage()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Button_ShopNow'), 20)
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Button_ShopNow'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/btn_view_next'), 20)
	WebUI.click(findTestObject('Object Repository/User/HomePage/btn_view_next'))
	
	WebUI.delay(2)
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Button_ShopNow'))

