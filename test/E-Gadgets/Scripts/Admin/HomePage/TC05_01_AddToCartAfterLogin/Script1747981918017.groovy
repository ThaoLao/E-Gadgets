import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.model.FailureHandling
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

WebUI.openBrowser('http://localhost:8081/login')
WebUI.maximizeWindow()


WebUI.setText(findTestObject("Page_Login/Input_Username"), "linh@gmail.com")
WebUI.setText(findTestObject("Page_Login/Input_Password"), "123456")

WebUI.waitForElementClickable(findTestObject("Page_Login/Button_Login"), 5)

WebUI.click(findTestObject("Page_Login/Button_Login"))

WebUI.delay(3)

WebUI.verifyNotMatch(WebUI.getUrl(), '.*login.*', true)

def element = WebUI.findWebElement(findTestObject("Homepage/Button_AddToCart"), 5)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(element))

WebUI.delay(3)

if (WebUI.verifyAlertPresent(5, FailureHandling.OPTIONAL)) {
	String alertText = WebUI.getAlertText()
	if (alertText.contains("Thêm sản phẩm vào giỏ thành công")) {
		WebUI.acceptAlert()
		WebUI.comment("Thêm thành công vào giỏ hàng")
	}
} else {
	WebUI.comment("Không có alert được hiển thị")
}

WebUI.closeBrowser()
