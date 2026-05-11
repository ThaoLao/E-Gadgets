import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import utils.openPage

	new openPage().navigateToHomePage()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Button_AddToWishlist'), 20)
	WebUI.scrollToElement(findTestObject('Object Repository/User/HomePage/Button_AddToWishlist'), 5)
	WebUI.click(findTestObject('Object Repository/User/HomePage/Button_AddToWishlist'))
	
	if (WebUI.verifyAlertPresent(5, FailureHandling.OPTIONAL)) {
		String alertText = WebUI.getAlertText()
		if (alertText.contains("đăng nhập")) {
			WebUI.acceptAlert()
			WebUI.waitForPageLoad(10)
			WebUI.verifyMatch(WebUI.getUrl(), '.*login.*', true)
		} else {
			WebUI.acceptAlert()
			WebUI.comment("Thêm thành công vào danh sách yêu thích")
		}
	} else {
		WebUI.comment("Không có alert được hiển thị")
	}