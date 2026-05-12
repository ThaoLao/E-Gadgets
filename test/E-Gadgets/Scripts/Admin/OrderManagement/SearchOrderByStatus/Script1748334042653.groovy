import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
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
	
	generalFunction fc = new generalFunction()
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
	
	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/OrderManagement/Table_Order_List'))
	WebUI.verifyElementText(findTestObject('Object Repository/Admin/OrderManagement/Order_Management_lbl'), "Danh Sách Đơn Hàng")
	
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Filter_bar'))
	WebUI.delay(2)
	// Lấy TestObject từ Object Repository
	TestObject filterList = findTestObject('Object Repository/Admin/OrderManagement/Filter_List_Option')
	
	// Lấy XPath từ TestObject
	String xpath = filterList.findPropertyValue('xpath')
	
	// Lấy tất cả các phần tử option
	List<WebElement> filterOptions = driver.findElements(By.xpath(xpath))
	
	// ===== Lọc trạng thái "Processing" (phần tử thứ 2 - index 1) =====
	filterOptions.get(1).click()
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/btn_Filter'))
	WebUI.delay(2)
	
	TestObject detailButtons = findTestObject('Object Repository/Admin/OrderManagement/List_Button_Detail')
	int maxOrders = 1
	String expectedStatus = "Processing"
	
	// Lặp kiểm tra 2 đơn hàng
	for (int i = 0; i < maxOrders; i++) {
	    // Lấy lại danh sách mới mỗi lần để tránh stale element
	    List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
	
	    // Nếu số lượng phần tử không đủ thì thoát loop
	    if (detailButtonElements.size() <= i) {
	        break
	    }
	
	    detailButtonElements.get(i).click()
	    WebUI.delay(2)
	
	    TestObject detailInfoList = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
	    List<WebElement> detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
	
	    if (detailInfoElements.size() > 6) {
	        String fullText = detailInfoElements.get(6).getText().trim()
	        String actualStatus = fullText.split(":")[1].trim()
	        WebUI.verifyEqual(actualStatus.toLowerCase(), expectedStatus.toLowerCase(), FailureHandling.CONTINUE_ON_FAILURE)
	    } else {
	        WebUI.verifyGreaterThan(detailInfoElements.size(), 6, FailureHandling.CONTINUE_ON_FAILURE)
	    }
	
	    // Click trở lại danh sách đơn hàng
	    WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Back_List_Orderf'))
	    WebUI.delay(2)
	}
	
	// Lọc trạng thái "Pending" 

	filterOptions = driver.findElements(By.xpath(xpath))
	filterOptions.get(2).click()
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/btn_Filter'))
	WebUI.delay(2)
	
	expectedStatus = "Pending"
	
	for (int i = 0; i < maxOrders; i++) {
	    // Lấy lại danh sách mới mỗi lần để tránh stale element
	    List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
	
	    if (detailButtonElements.size() <= i) {
	        break
	    }
	    detailButtonElements.get(i).click()
	    WebUI.delay(2)
	    TestObject detailInfoList = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
	    List<WebElement> detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
	    if (detailInfoElements.size() > 6) {
	        String fullText = detailInfoElements.get(6).getText().trim()
	        String actualStatus = fullText.split(":")[1].trim()
	        WebUI.verifyEqual(actualStatus.toLowerCase(), expectedStatus.toLowerCase(), FailureHandling.CONTINUE_ON_FAILURE)
	    } else {
	        WebUI.verifyGreaterThan(detailInfoElements.size(), 6, FailureHandling.CONTINUE_ON_FAILURE)
	    }
	
	    // Click trở lại danh sách đơn hàng
	    WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Back_List_Orderf'))
	    WebUI.delay(2)
	}
	
	// Lọc trạng thái "Delivering"
	
		filterOptions = driver.findElements(By.xpath(xpath))
		filterOptions.get(3).click()
		WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/btn_Filter'))
		WebUI.delay(2)
		
		expectedStatus = "Delivering"
		
		for (int i = 0; i < maxOrders; i++) {
			// Lấy lại danh sách mới mỗi lần để tránh stale element
			List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
		
			if (detailButtonElements.size() <= i) {
				break
			}
			detailButtonElements.get(i).click()
			WebUI.delay(2)
			TestObject detailInfoList = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
			List<WebElement> detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
			if (detailInfoElements.size() > 6) {
				String fullText = detailInfoElements.get(6).getText().trim()
				String actualStatus = fullText.split(":")[1].trim()
				WebUI.verifyEqual(actualStatus.toLowerCase(), expectedStatus.toLowerCase(), FailureHandling.CONTINUE_ON_FAILURE)
			} else {
				WebUI.verifyGreaterThan(detailInfoElements.size(), 6, FailureHandling.CONTINUE_ON_FAILURE)
			}
		
			// Click trở lại danh sách đơn hàng
			WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Back_List_Orderf'))
			WebUI.delay(2)
		}
		
		// Lọc trạng thái "Delivered"
		
			filterOptions = driver.findElements(By.xpath(xpath))
			filterOptions.get(4).click()
			WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/btn_Filter'))
			WebUI.delay(2)
			
			expectedStatus = "Delivered"
			
			for (int i = 0; i < maxOrders; i++) {
				// Lấy lại danh sách mới mỗi lần để tránh stale element
				List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
			
				if (detailButtonElements.size() <= i) {
					break
				}
				detailButtonElements.get(i).click()
				WebUI.delay(2)
				TestObject detailInfoList = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
				List<WebElement> detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
				if (detailInfoElements.size() > 6) {
					String fullText = detailInfoElements.get(6).getText().trim()
					String actualStatus = fullText.split(":")[1].trim()
					WebUI.verifyEqual(actualStatus.toLowerCase(), expectedStatus.toLowerCase(), FailureHandling.CONTINUE_ON_FAILURE)
				} else {
					WebUI.verifyGreaterThan(detailInfoElements.size(), 6, FailureHandling.CONTINUE_ON_FAILURE)
				}
			
				// Click trở lại danh sách đơn hàng
				WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Back_List_Orderf'))
				WebUI.delay(2)
			}
			
			// Lọc trạng thái "Cancelled"
			
				filterOptions = driver.findElements(By.xpath(xpath))
				filterOptions.get(5).click()
				WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/btn_Filter'))
				WebUI.delay(2)
				
				expectedStatus = "Cancelled"
				
				for (int i = 0; i < maxOrders; i++) {
					// Lấy lại danh sách mới mỗi lần để tránh stale element
					List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
				
					if (detailButtonElements.size() <= i) {
						break
					}
					detailButtonElements.get(i).click()
					WebUI.delay(2)
					TestObject detailInfoList = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
					List<WebElement> detailInfoElements = WebUiCommonHelper.findWebElements(detailInfoList, 10)
					if (detailInfoElements.size() > 6) {
						String fullText = detailInfoElements.get(6).getText().trim()
						String actualStatus = fullText.split(":")[1].trim()
						WebUI.verifyEqual(actualStatus.toLowerCase(), expectedStatus.toLowerCase(), FailureHandling.CONTINUE_ON_FAILURE)
					} else {
						WebUI.verifyGreaterThan(detailInfoElements.size(), 6, FailureHandling.CONTINUE_ON_FAILURE)
					}
				
					// Click trở lại danh sách đơn hàng
					WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Back_List_Orderf'))
					WebUI.delay(2)
				}
				
				SessionHelper.endLoggedInSessionCleanly()
