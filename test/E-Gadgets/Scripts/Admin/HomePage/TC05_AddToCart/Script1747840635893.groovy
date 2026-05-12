import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.model.FailureHandling
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

WebUI.openBrowser('http://localhost:8080')
WebUI.maximizeWindow()

def element = WebUI.findWebElement(findTestObject("Homepage/Button_AddToCart"), 5)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(element))

WebUI.delay(3)

if (WebUI.verifyAlertPresent(5, FailureHandling.OPTIONAL)) {
	String alertText = WebUI.getAlertText()
	if (alertText.contains("đăng nhập")) {
		WebUI.acceptAlert()
		WebUI.delay(3)
		WebUI.verifyMatch(WebUI.getUrl(), '.*login.*', true)
	} else {
		WebUI.acceptAlert()
		WebUI.delay(3)
		WebUI.comment("Thêm thành công vào danh sách yêu thích")
	}
} else {
	WebUI.comment("Không có alert được hiển thị")
}

WebUI.closeBrowser()
