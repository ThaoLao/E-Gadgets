import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat
import java.util.Date

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import utils.*

	WebUI.callTestCase(
		findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
		[('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	

	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/Admin/ContactManagement/Button_Contact'))
	WebUI.delay(2)
	
	// === Chọn Sort By "Cũ nhất" ===
	TestObject sortDropdown = findTestObject('Object Repository/Admin/ContactManagement/Dropdown_SortBy')
	WebUI.selectOptionByLabel(sortDropdown, 'Cũ nhất', false)
	WebUI.delay(2)
	

	TestObject contactTableRows = findTestObject('Object Repository/Admin/ContactManagement/table_ContactRows')
	List<WebElement> rows = WebUiCommonHelper.findWebElements(contactTableRows, 10)
	

	if (rows.size() >= 2) {
		String dateStr1 = rows[0].findElements(By.tagName("td"))[5].getText().trim()
		String dateStr2 = rows[1].findElements(By.tagName("td"))[5].getText().trim()
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		Date date1 = sdf.parse(dateStr1)
		Date date2 = sdf.parse(dateStr2)
	
		if (date1.before(date2)) {
			return true
		} else if (date1.after(date2)) {
			return false
		} else {
			return true
		}
	} else {
		WebUI.comment("Không đủ 2 dòng liên hệ để so sánh.")
		return false
	}
	SessionHelper.endLoggedInSessionCleanly()