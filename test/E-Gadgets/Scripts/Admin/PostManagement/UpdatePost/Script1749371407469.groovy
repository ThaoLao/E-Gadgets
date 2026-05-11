import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

import utils.*

	// Khởi tạo hàm phụ trợ
	generalFunction fc = new generalFunction()
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	
	// Đăng nhập
	WebUI.callTestCase(
		findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
		[('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	
	// Truy cập trang bài viết
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Post'))
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Post_Management'))
	WebUI.delay(2)
	
	// Lấy dòng đầu tiên
	TestObject tableRows = findTestObject('Object Repository/Admin/PostManagement/table_PostRows')
	List<WebElement> postRows = WebUiCommonHelper.findWebElements(tableRows, 10)
	
	if (postRows.size() > 0) {
		WebElement firstRow = postRows[0]
		List<WebElement> cols = firstRow.findElements(By.tagName("td"))
	
		String titleBefore = cols[1].getText().trim()
		String authorBefore = cols[2].getText().trim()
		String summaryBefore = cols[4].getText().trim()
	
		// Click "Cập nhật"
		TestObject updateButtons = findTestObject('Object Repository/Admin/PostManagement/List_Button_Update')
		List<WebElement> updateBtns = WebUiCommonHelper.findWebElements(updateButtons, 10)
		WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(updateBtns[0]))
	
	
		// Xác minh dữ liệu ban đầu
		String formTitle = WebUI.getAttribute(findTestObject('Object Repository/Admin/PostManagement/Input_BlogTitle'), 'value')
		String formSummary = WebUI.getAttribute(findTestObject('Object Repository/Admin/PostManagement/Input_Summary'), 'value')
		WebUI.verifyMatch(formTitle, titleBefore, false)
		WebUI.verifyMatch(formSummary, summaryBefore, false)
	
		// Thực hiện cập nhật nội dung
		String updatedSummary = summaryBefore + ' [UPDATED]'
		WebUI.setText(findTestObject('Object Repository/Admin/PostManagement/Input_Summary'), updatedSummary)
		WebUI.setText(findTestObject('Object Repository/Admin/PostManagement/Input_Content'), 'Nội dung mới đã được cập nhật vào ngày test.')
	
		// Click "Xác nhận" cập nhật
		WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Submit'))
		WebUI.delay(2)
	
		// Kiểm tra thông báo thành công
		WebUI.verifyElementText(findTestObject('Object Repository/Admin/PostManagement/Alert_Success'), 'Cập nhật bài viết thành công!')
	
		// Ghi log vào Excel
		util.setData(excelPath, 'UpdatedPost_Title', formTitle)
		util.setData(excelPath, 'UpdatedPost_Summary', updatedSummary)
		util.setData(excelPath, 'UpdateStatus', 'Success')
	} else {
		return false
	}
	SessionHelper.endLoggedInSessionCleanly()
