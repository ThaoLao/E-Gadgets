import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import utils.openPage

	// Navigate to homepage
	new openPage().navigateToHomePage()
	
	// Ensure we're on homepage and not logged in
	WebUI.verifyMatch(WebUI.getUrl(), '.*localhost.*', true)
	
	// Wait for add to cart button to be visible
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Button_AddToCart'), 20)
	WebUI.scrollToElement(findTestObject('Object Repository/User/HomePage/Button_AddToCart'), 5)
	
	// Click add to cart button
	WebUI.click(findTestObject('Object Repository/User/HomePage/Button_AddToCart'))
	
	// Wait for AJAX call to complete and alert to appear
	WebUI.waitForAlert(10)
	
	// Verify the expected alert message for non-logged-in users
	String alertText = WebUI.getAlertText()
	WebUI.comment("Alert text received: " + alertText)
	
	// The expected message should be "Bạn cần đăng nhập trước"
WebUI.comment("Expected login required message, but got: " + alertText)
WebUI.verifyEqual(alertText.contains("đăng nhập") || alertText.contains("Bạn cần đăng nhập"), true, FailureHandling.CONTINUE_ON_FAILURE)
	
	// Accept the alert
	WebUI.acceptAlert()
	
	// Wait for redirect to login page
	WebUI.waitForPageLoad(15)
	
	// Verify redirect to login page
	WebUI.verifyMatch(WebUI.getUrl(), '.*login.*', true, 
		"Expected redirect to login page after add to cart without authentication")
	
	WebUI.comment("✅ Test passed: Add to cart without login correctly shows alert and redirects to login page")