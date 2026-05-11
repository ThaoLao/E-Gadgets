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
	
	generalFunction fc = new generalFunction()
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Post'))
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Post_Management'))
	WebUI.delay(2)
	
	// Lấy dòng đầu tiên của bảng bài viết
	TestObject tableRows = findTestObject('Object Repository/Admin/PostManagement/table_PostRows')
	List<WebElement> postRows = WebUiCommonHelper.findWebElements(tableRows, 10)
	
	if (postRows.size() > 0) {
		WebElement firstRow = postRows[0]
		List<WebElement> cols = firstRow.findElements(By.tagName("td"))
		String postID = cols[0].getText().trim()
		String postTitle = cols[1].getText().trim()
	
		// Ghi log Excel thông tin chuẩn bị xóa
		util.setData(excelPath, "DeletedPost_ID", postID)
		util.setData(excelPath, "DeletedPost_Title", postTitle)
	
		// Click nút "Xóa"
		TestObject deleteButtons = findTestObject('Object Repository/Admin/PostManagement/List_Button_Delete')
		List<WebElement> deleteBtns = WebUiCommonHelper.findWebElements(deleteButtons, 10)
		WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(deleteBtns[0]))
	
		// Xác nhận alert
		WebUI.waitForAlert(3)
		WebUI.acceptAlert()
	
		WebUI.delay(2)
	
		// Xác minh bài viết đã bị xoá 
		List<WebElement> updatedRows = WebUiCommonHelper.findWebElements(tableRows, 10)
		boolean stillExists = false
	
		for (WebElement row : updatedRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"))
			if (cells[0].getText().trim() == postID || cells[1].getText().trim() == postTitle) {
				stillExists = true
				break
			}
		}
	
		if (!stillExists) {
			return true
			util.setData(excelPath, "DeletedPost_Status", "Success")
		} else {
			return false
			util.setData(excelPath, "DeletedPost_Status", "Failed")
		}
	} else {
		return false
	}
	
	SessionHelper.endLoggedInSessionCleanly()