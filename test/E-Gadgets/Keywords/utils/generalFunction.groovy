package utils
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

public class generalFunction {

	@Keyword
	def scrollDown(int pixels = 600) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		js.executeScript("window.scrollBy(0, arguments[0]);", pixels)
	}
	@Keyword
	def scrollUp(int pixels = 600) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		js.executeScript("window.scrollBy(0, -arguments[0]);", pixels)
	}

	@Keyword
	def scrollToTop() {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		js.executeScript("window.scrollTo(0, 0);")
	}

	@Keyword
	def scrollToBottom() {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);")
	}

	@Keyword
	def scrollRight() {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		js.executeScript("window.scrollBy(document.body.scrollWidth, 0);")
	}

	@Keyword
	def scrollLeft() {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		js.executeScript("window.scrollBy(-document.body.scrollWidth, 0);")
	}

	@Keyword
	def scrollByOffset(int x, int y) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y)
	}

	@Keyword
	def clickNthElementInList(TestObject listObject, int indexToClick) {
		WebDriver driver = DriverFactory.getWebDriver()

		String xpath = listObject.findPropertyValue("xpath")
		List<WebElement> elements = driver.findElements(By.xpath(xpath))

		if (elements == null || elements.isEmpty()) {
			KeywordUtil.markFailed("❌ Không tìm thấy phần tử nào với XPath: ${xpath}")
			return
		}

		if (indexToClick >= 0 && indexToClick < elements.size()) {
			elements.get(indexToClick).click()
			KeywordUtil.logInfo("✅ Click vào phần tử tại index ${indexToClick} thành công.")
		} else {
			KeywordUtil.markFailed("❌ Index ${indexToClick} vượt quá số phần tử (${elements.size()}) trong danh sách.")
		}
	}
}
