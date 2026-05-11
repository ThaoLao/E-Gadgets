package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import internal.GlobalVariable

/**
 * Central place for session isolation: UI logout and cookie reset so suites do not leak auth state.
 */
class SessionHelper {

	private static final String HP = 'Object Repository/User/HomePage/'

	/** True if a WebDriver session exists. */
	static boolean hasWebDriver() {
		try {
			DriverFactory.getWebDriver()
			return true
		} catch (Exception ignored) {
			return false
		}
	}

	/**
	 * Navigate to site root, then clear all cookies (stronger than only clicking logout).
	 * Call after tests that logged in, or from a Test Listener when the browser is kept open.
	 */
	@Keyword
	static void clearCookiesOnCurrentSite() {
		if (!hasWebDriver()) {
			return
		}
		String home = String.valueOf(GlobalVariable.HomePage_url)
		WebUI.navigateToUrl(home)
		WebUI.waitForPageLoad(20)
		WebUI.deleteAllCookies()
		WebUI.navigateToUrl(home)
		WebUI.waitForPageLoad(20)
	}

	/**
	 * Opens account menu and uses the same logout paths as your existing scripts (guest menu or admin button).
	 */
	@Keyword
	static void logoutViaUiIfPresent() {
		if (!hasWebDriver()) {
			return
		}
		if (WebUI.verifyElementPresent(findTestObject(HP + 'icon_Accounts'), 3, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject(HP + 'icon_Accounts'))
			WebUI.waitForElementPresent(findTestObject(HP + 'header_menu_link_logout'), 5, FailureHandling.OPTIONAL)
			if (WebUI.verifyElementPresent(findTestObject(HP + 'header_menu_link_logout'), 2, FailureHandling.OPTIONAL)) {
				try {
					WebUI.click(findTestObject(HP + 'header_menu_link_logout'))
				} catch (Exception e) {
					// Nếu vẫn lỗi "Not Interactable", dùng JavaScript click làm phương án cuối
					WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUiCommonHelper.findWebElement(findTestObject(HP + 'header_menu_link_logout'), 2)))
				}
				WebUI.waitForPageLoad(10)
				return
			}
		}
	}

	/** UI logout (best-effort) then cookie clear — safest default after a logged-in flow. */
	@Keyword
	static void endLoggedInSessionCleanly() {
		if (!hasWebDriver()) {
			return
		}
		logoutViaUiIfPresent()
		clearCookiesOnCurrentSite()
	}

	/** Use in @AfterTestCase when the browser may be reused between test cases. */
	@Keyword
	static void resetWebUiSessionForNextTest() {
		clearCookiesOnCurrentSite()
	}
}
