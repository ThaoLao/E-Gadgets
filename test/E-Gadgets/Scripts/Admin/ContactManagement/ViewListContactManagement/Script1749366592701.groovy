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
	WebUI.click(findTestObject('Object Repository/Admin/ContactManagement/Button_Contact'))
	WebUI.delay(2)
	
	
	// Lấy danh sách hàng trong bảng sản phẩm
	TestObject tableRows = findTestObject('Object Repository/Admin/ContactManagement/table_ProductRows')
	List<WebElement> contactRows = WebUiCommonHelper.findWebElements(tableRows, 10)
	int maxRows = Math.min(contactRows.size(), 4)
	
	// Duyệt 4 dòng đầu và ghi dữ liệu vào Excel
	for (int i = 0; i < maxRows; i++) {
		WebElement row = contactRows.get(i)
		List<WebElement> cols = row.findElements(By.tagName("td"))
		
		String id = cols.get(0).getText().trim()
		String name = cols.get(1).getText().trim()
		String email = cols.get(2).getText().trim()
		String subject = cols.get(3).getText().trim()
		String message = cols.get(4).getText().trim()
		String createdAt = cols.get(5).getText().trim()
		
		util.setData(excelPath, "ContactID${i+1}", id)
		util.setData(excelPath, "ContactName${i+1}", name)
		util.setData(excelPath, "ContactEmail${i+1}", email)
		util.setData(excelPath, "ContactSubject${i+1}", subject)
		util.setData(excelPath, "ContactMessage${i+1}", message)
		util.setData(excelPath, "ContactCreatedAt${i+1}", createdAt)
	}
	
	// (Tùy chọn) xác nhận có các nút "Phản hồi" và "Xoá"
	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/ContactManagement/List_Button_Reply'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/ContactManagement/List_Button_Delete'))
	
	SessionHelper.endLoggedInSessionCleanly()
	
	