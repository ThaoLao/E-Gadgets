import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import internal.GlobalVariable
import utils.openLoginPage
import utils.dataFileUtil
import utils.SessionHelper

try {
	new openLoginPage().navigateToLogin()
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	String email = util.getData(excelPath, 'register_email', 'Register_Success') ?: GlobalVariable.User_Email
	String password = util.getData(excelPath, 'register_password', 'Register_Success') ?: String.valueOf(GlobalVariable.General_Password)

	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/Email'), 15)
	WebUI.setText(findTestObject('Object Repository/User/Auth/Email'), email)
	WebUI.setText(findTestObject('Object Repository/User/Auth/Password'), password)
	WebUI.click(findTestObject('Object Repository/User/Auth/btn_login'))
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/Button_AddToWishlist'), 20)

	WebUI.scrollToElement(findTestObject('Object Repository/User/HomePage/Button_AddToWishlist'), 5)
	WebUI.click(findTestObject('Object Repository/User/HomePage/Button_AddToWishlist'))

	if (WebUI.verifyAlertPresent(5, FailureHandling.OPTIONAL)) {
		String alertText = WebUI.getAlertText()
		if (alertText.contains("Đã thêm vào danh sách yêu thích")) {
			WebUI.acceptAlert()
			WebUI.comment("Thêm thành công vào danh sách yêu thích")
		}
	} else {
		WebUI.comment("Không có alert được hiển thị")
	}
} finally {
	SessionHelper.endLoggedInSessionCleanly()
}