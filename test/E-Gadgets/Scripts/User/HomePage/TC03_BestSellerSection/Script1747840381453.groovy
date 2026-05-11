import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import utils.openPage

	new openPage().navigateToHomePage()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Section_BestSeller'), 20)
	WebUI.scrollToElement(findTestObject('Object Repository/User/HomePage/Section_BestSeller'), 5)
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/Section_BestSeller'))
