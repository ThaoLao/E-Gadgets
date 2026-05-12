import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

import utils.*


	WebUI.callTestCase(
		findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
		[('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'), 3)
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'))
	WebUI.delay(3)
	
	// Lọc đơn hàng có trạng thái "PROCESSING"
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Filter_bar'))
	TestObject filterList = findTestObject('Object Repository/Admin/OrderManagement/Filter_List_Option')
	String xpath = filterList.findPropertyValue('xpath')
	List<WebElement> filterOptions = driver.findElements(By.xpath(xpath))
	filterOptions.get(3).click() // index 3 tương ứng "Processing"
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/btn_Filter'))
	WebUI.delay(2)
	
	// Mở chi tiết đơn hàng đầu tiên
	TestObject detailButtons = findTestObject('Object Repository/Admin/OrderManagement/List_Button_Detail')
	List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
	if (detailButtonElements.size() > 0) {
		detailButtonElements.get(0).click()
		WebUI.delay(2)
	}
	
	// Kiểm tra trạng thái hiện tại
	TestObject detailInfoList = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
	List<WebElement> detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
	
	if (detailInfoElements.size() > 6) {
		String fullText = detailInfoElements.get(6).getText().trim()
		String currentStatus = fullText.split(":")[1].trim()
	
		if (currentStatus.equalsIgnoreCase("Processing")) {
			
			String action = "cancel" 
			
			if (action == "deliver") {
				WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Deliver_Order'))
				WebUI.delay(2)
			} else if (action == "cancel") {
				WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Cancel_Order'))
				WebUI.delay(2)
			}
	
			// Kiểm tra trạng thái đã cập nhật thành công
			detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
			String updatedText = detailInfoElements.get(6).getText().trim()
			String updatedStatus = updatedText.split(":")[1].trim()
	
			if (action == "deliver") {
				WebUI.verifyEqual(updatedStatus.toLowerCase(), "deliverring")
			} else if (action == "cancel") {
				WebUI.verifyEqual(updatedStatus.toLowerCase(), "cancelled")
			}
	
		} 
	} 
	
	SessionHelper.endLoggedInSessionCleanly()