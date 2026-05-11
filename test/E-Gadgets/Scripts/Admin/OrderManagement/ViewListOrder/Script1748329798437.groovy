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
	
	WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'), 3)
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'))
	WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/OrderManagement/Button_Order_Management'), 3)
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Order_Management'))
	WebUI.delay(3)
	
	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/OrderManagement/Table_Order_List'))
	WebUI.verifyElementText(findTestObject('Object Repository/Admin/OrderManagement/Order_Management_lbl'),"Danh Sách Đơn Hàng" )
	
	int rowCount = WebUI.findWebElements(findTestObject('Object Repository/Admin/OrderManagement/Table_OrderRows'), 10).size()
	WebUI.verifyGreaterThan(rowCount, 0)
	
	TestObject tableRows = findTestObject('Object Repository/Admin/OrderManagement/Table_OrderRows')
	
	List<WebElement> orderRows = WebUiCommonHelper.findWebElements(tableRows, 10)
	
	int maxRows = Math.min(orderRows.size(), 4)
	
	for (int i = 0; i < maxRows; i++) {
		WebElement row = orderRows.get(i)
		
		List<WebElement> cols = row.findElements(By.tagName("td"))
		
		String orderId = cols.get(0).getText().trim()
		String orderCode = cols.get(1).getText().trim()
		String receiver = cols.get(2).getText().trim()
		String phone = cols.get(3).getText().trim()
		String email = cols.get(4).getText().trim()
		String createDate = cols.get(5).getText().trim()
		String totalPrice = cols.get(6).getText().trim()
		String status = cols.get(7).getText().trim()
		String paymentMethod = cols.get(8).getText().trim()
		
		util.setData(excelPath, "OrderID" + (i+1), orderId)
		util.setData(excelPath, "OrderCode" + (i+1), orderCode)
		util.setData(excelPath, "Receiver" + (i+1), receiver)
		util.setData(excelPath, "Phone" + (i+1), phone)
		util.setData(excelPath, "Email" + (i+1), email)
		util.setData(excelPath, "CreateDate" + (i+1), createDate)
		util.setData(excelPath, "TotalPrice" + (i+1), totalPrice)
		util.setData(excelPath, "Status" + (i+1), status)
		util.setData(excelPath, "PaymentMethod" + (i+1), paymentMethod)
	}
	
	SessionHelper.endLoggedInSessionCleanly()
	
	