package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import internal.GlobalVariable

public class openShopPage {
	@Keyword
	def navigateToShop() {
		boolean browserOpen = false
		try {
			DriverFactory.getWebDriver()
			browserOpen = true
		} catch (Exception e) {
			browserOpen = false
		}
		if (!browserOpen) {
			WebUI.openBrowser('')
			WebUI.maximizeWindow()
		}
		String base = String.valueOf(GlobalVariable.HomePage_url).replaceAll('/$', '')
		WebUI.navigateToUrl(base + '/shop')
		WebUI.waitForPageLoad(25)
	}
}
