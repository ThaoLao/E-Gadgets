import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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
	
	// Lấy danh sách nút "Xóa"
	TestObject btnDeleteList = findTestObject('Object Repository/Admin/ContactManagement/List_Button_Delete') 
	List<WebElement> deleteBtns = WebUiCommonHelper.findWebElements(btnDeleteList, 10)
	
	if (deleteBtns.size() > 0) {
		TestObject tableRows = findTestObject('Object Repository/Admin/ContactManagement/table_ContactRows') 
		List<WebElement> rows = WebUiCommonHelper.findWebElements(tableRows, 10)
	
		if (rows.size() > 0) {
			// Lấy email dòng đầu để so sánh sau khi xóa
			String emailBeforeDelete = rows[0].findElements(By.tagName("td"))[2].getText().trim()
	
			// Click nút Xóa dòng đầu
			WebUI.executeJavaScript("arguments[0].scrollIntoView(true);", Arrays.asList(deleteBtns[0]))
			WebUI.delay(1)
			WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(deleteBtns[0]))
	
			// Xử lý Alert xác nhận
			WebUI.waitForAlert(3)
			WebUI.acceptAlert()
			WebUI.delay(2)
	
			// Lấy lại danh sách dòng sau khi xóa
			List<WebElement> rowsAfter = WebUiCommonHelper.findWebElements(tableRows, 10)
	
			boolean stillExists = false
			for (WebElement row : rowsAfter) {
				List<WebElement> cols = row.findElements(By.tagName("td"))
				if (cols.size() >= 3) {
					String email = cols[2].getText().trim()
					if (email == emailBeforeDelete) {
						stillExists = true
						break
					}
				}
			}
	
			if (!stillExists) {
				
				return true
			} else {
				return false
			}
		} else {
			return false
		}
	} else {
		return false
	}
	
	SessionHelper.endLoggedInSessionCleanly()
