import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

WebUI.openBrowser('http://localhost:8080')
WebUI.maximizeWindow()

WebUI.verifyElementPresent(findTestObject('Homepage/Button_ShopNow'), 10)

// Đợi slide tự động chuyển (giả định có animation)
WebUI.delay(3)

// Nếu có nút điều hướng: WebUI.click(findTestObject('Homepage/Button_SliderNext'))

WebUI.closeBrowser()