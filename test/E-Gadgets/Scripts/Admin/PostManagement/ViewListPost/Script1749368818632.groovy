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
	
	
	// Lấy danh sách hàng trong bảng sản phẩm
	TestObject tableRows = findTestObject('Object Repository/Admin/PostManagement/table_ProductRows')
	List<WebElement> postRows = WebUiCommonHelper.findWebElements(tableRows, 10)
	int maxRows = Math.min(postRows.size(), 4)
	
	// Duyệt 4 dòng đầu và ghi dữ liệu vào Excel
	for (int i = 0; i < maxRows; i++) {
		WebElement row = postRows.get(i)
		List<WebElement> cols = row.findElements(By.tagName("td"))
		
		String id = cols[0].getText().trim()
		String title = cols[1].getText().trim()
		String author = cols[2].getText().trim()
		String date = cols[3].getText().trim()
		String summary = cols[4].getText().trim()
	
		util.setData(excelPath, "PostID${i+1}", id)
		util.setData(excelPath, "PostTitle${i+1}", title)
		util.setData(excelPath, "PostAuthor${i+1}", author)
		util.setData(excelPath, "PostDate${i+1}", date)
		util.setData(excelPath, "PostSummary${i+1}", summary)
	}
	

	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/PostManagement/List_Button_Update'))
	
	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/PostManagement/List_Button_Delete'))
	
	SessionHelper.endLoggedInSessionCleanly()
	
	