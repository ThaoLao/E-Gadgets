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

	'Đăng nhập admin'
	WebUI.callTestCase(
	    findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
	    [('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	WebDriver driver = DriverFactory.getWebDriver()
	'1. Truy cập trang quản lý đơn hàng'
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'), 3)
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'))
	WebUI.delay(3)
	
	'2. Lọc đơn hàng trạng thái Pending'
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Filter_bar'))
	WebUI.delay(2)
	TestObject filterList = findTestObject('Object Repository/Admin/OrderManagement/Filter_List_Option')
	String xpath = filterList.findPropertyValue('xpath')
	List<WebElement> filterOptions = driver.findElements(By.xpath(xpath))
	filterOptions.get(2).click() // chọn "Pending"
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/btn_Filter'))
	WebUI.delay(2)
	
	'3. Lấy nút Chi tiết đơn hàng đầu tiên'
	TestObject detailButtons = findTestObject('Object Repository/Admin/OrderManagement/List_Button_Detail')
	List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
	if (detailButtonElements.size() > 0) {
	    // Click vào chi tiết đơn hàng đầu tiên
	    detailButtonElements.get(0).click()
	    WebUI.delay(2)
	    // Kiểm tra trạng thái đơn hàng
	    TestObject detailInfoList = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
	    List<WebElement> detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
	    if (detailInfoElements.size() > 6) {
	        String fullText = detailInfoElements.get(6).getText().trim()
	        String currentStatus = fullText.split(":")[1].trim()
	        if (currentStatus.equalsIgnoreCase("Pending")) {
	            // Xử lý đơn hàng: ví dụ xử lý (processOrder = true)
	            boolean processOrder = true
	            if (processOrder) {
	                WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Process_Order'))
	                WebUI.delay(2)
	            } else {
	                WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Cancelled_Order'))
	                WebUI.delay(2)
	            }
	            // Kiểm tra trạng thái cập nhật lại
	            detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
	            fullText = detailInfoElements.get(6).getText().trim()
	            String updatedStatus = fullText.split(":")[1].trim()
	            WebUI.verifyEqual(updatedStatus.toLowerCase(), processOrder ? "processing" : "cancelled")
	        } 
	    }
	  	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Back_List_Orderf'))
	    WebUI.delay(2)
	}
	
	SessionHelper.endLoggedInSessionCleanly()
