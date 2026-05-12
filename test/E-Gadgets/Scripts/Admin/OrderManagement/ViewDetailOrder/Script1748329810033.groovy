import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

import utils.*

	WebUI.callTestCase(
		findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
		[('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	generalFunction fc = new generalFunction()
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	
	WebUI.waitForElementClickable(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'), 3)
	WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Order'))
	WebUI.delay(3)
	
	
	fc.scrollUp()
	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/OrderManagement/Table_Order_List'))
	WebUI.verifyElementText(findTestObject('Object Repository/Admin/OrderManagement/Order_Management_lbl'),"Danh Sách Đơn Hàng")
	
	// Lấy danh sách nút Chi tiết
	TestObject detailButtons = findTestObject('Object Repository/Admin/OrderManagement/List_Button_Detail')
	List<WebElement> detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
	
	// Giới hạn lấy 4 đơn hàng đầu tiên hoặc ít hơn nếu có ít hơn 4
	int maxOrders = Math.min(4, detailButtonElements.size())
	
	for (int orderIndex = 0; orderIndex < maxOrders; orderIndex++) {
	    
	    // Click nút Chi tiết của đơn hàng thứ orderIndex
	    detailButtonElements.get(orderIndex).click()
	    WebUI.delay(2)
	    
	    // Kiểm tra trang chi tiết đơn hàng
	    WebUI.verifyElementText(findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_lbl'), "Thông tin chi tiết đơn hàng")
	    
	    // Lấy thông tin chi tiết đơn hàng (label-value)
	    TestObject infoParagraphsTO = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Info')
	    List<WebElement> infoParagraphs = WebUiCommonHelper.findWebElements(infoParagraphsTO, 10)
	    
	    for (WebElement p : infoParagraphs) {
	        try {
	            WebElement labelElement = p.findElement(By.tagName("strong"))
	            WebElement valueElement = p.findElement(By.tagName("span"))
	            
	            String label = labelElement.getText().trim()
	            if (label.endsWith(":")) {
	                label = label.substring(0, label.length() - 1)
	            }
	            String value = valueElement.getText().trim()
	            
	            // Lưu với key chứa số thứ tự đơn hàng để phân biệt
	            util.setData(excelPath, label + "_" + (orderIndex + 1), value)
	        } catch (Exception e) {
	        }
	    }
	    
	    // Lấy danh sách sản phẩm
	    TestObject productRowsTO = findTestObject('Object Repository/Admin/OrderManagement/Detail_Order_Product')
	    List<WebElement> productRows = WebUiCommonHelper.findWebElements(productRowsTO, 10)
	    
	    int productIndex = 1
	    for (WebElement row : productRows) {
	        try {
	            List<WebElement> cells = row.findElements(By.tagName("td"))
	            String productId = cells.get(0).getText().trim()
	            String productName = cells.get(1).getText().trim()
	            String author = cells.get(2).getText().trim()
	            String publisher = cells.get(3).getText().trim()
	            String publishDate = cells.get(4).getText().trim()
	            String category = cells.get(5).getText().trim()
	            String price = cells.get(6).getText().trim()
	            String discountPrice = cells.get(7).getText().trim()
	            String quantity = cells.get(8).getText().trim()
	            String totalPrice = cells.get(9).getText().trim()
	            
	            String baseKey = "Order" + (orderIndex + 1) + "_Product" + productIndex + "_"
	            util.setData(excelPath, baseKey + "ProductID", productId)
	            util.setData(excelPath, baseKey + "ProductName", productName)
	            util.setData(excelPath, baseKey + "Author", author)
	            util.setData(excelPath, baseKey + "Publisher", publisher)
	            util.setData(excelPath, baseKey + "PublishDate", publishDate)
	            util.setData(excelPath, baseKey + "Category", category)
	            util.setData(excelPath, baseKey + "Price", price)
	            util.setData(excelPath, baseKey + "DiscountPrice", discountPrice)
	            util.setData(excelPath, baseKey + "Quantity", quantity)
	            util.setData(excelPath, baseKey + "TotalPrice", totalPrice)
	            
	            productIndex++
	        } catch (Exception e) {
	        }
	    }
	    
	    // Quay lại danh sách đơn hàng để tiếp tục với đơn tiếp theo
	    WebUI.click(findTestObject('Object Repository/Admin/OrderManagement/Button_Back_List_Orderf'))
	    WebUI.delay(2)
	    
	    // Sau khi back, cập nhật lại danh sách nút detail vì DOM có thể thay đổi
	    detailButtonElements = WebUiCommonHelper.findWebElements(detailButtons, 10)
	}
	
	
	// Lấy danh sách dòng đơn hàng trong bảng (danh sách)
	TestObject tableRows = findTestObject('Object Repository/Admin/OrderManagement/Table_OrderRows')
	List<WebElement> orderRows = WebUiCommonHelper.findWebElements(tableRows, 10)
	
	int maxRows = Math.min(orderRows.size(), 4)
	
	for (int i = 0; i < maxRows; i++) {
		WebElement row = orderRows.get(i)
		List<WebElement> cols = row.findElements(By.tagName("td"))
	
		String uiOrderId = cols.get(0).getText().trim()
		String uiOrderCode = cols.get(1).getText().trim()
		String uiReceiver = cols.get(2).getText().trim()
		String uiPhone = cols.get(3).getText().trim()
		String uiEmail = cols.get(4).getText().trim()
		String uiCreateDate = cols.get(5).getText().trim()
		String uiTotalPrice = cols.get(6).getText().trim()
		String uiStatus = cols.get(7).getText().trim()
		String uiPaymentMethod = cols.get(8).getText().trim()
	
		// Lấy dữ liệu chi tiết đơn hàng đã lưu trong Excel

		String excelReceiver = util.getData(excelPath, "Receiver" + (i+1))
		excelReceiver = (excelReceiver == null) ? "" : excelReceiver.trim()
		
		String excelPhone = util.getData(excelPath, "Phone" + (i+1))
		excelPhone = (excelPhone == null) ? "" : excelPhone.trim()
		
		String excelEmail = util.getData(excelPath, "Email" + (i+1))
		excelEmail = (excelEmail == null) ? "" : excelEmail.trim()
		
		String excelCreateDate = util.getData(excelPath, "CreateDate" + (i+1))
		excelCreateDate = (excelCreateDate == null) ? "" : excelCreateDate.trim()
		
		String excelTotalPrice = util.getData(excelPath, "TotalPrice" + (i+1))
		excelTotalPrice = (excelTotalPrice == null) ? "" : excelTotalPrice.trim()
		
		String excelStatus = util.getData(excelPath, "Status" + (i+1))
		excelStatus = (excelStatus == null) ? "" : excelStatus.trim()
		
		String excelPaymentMethod = util.getData(excelPath, "PaymentMethod" + (i+1))
		excelPaymentMethod = (excelPaymentMethod == null) ? "" : excelPaymentMethod.trim()

	
		// So sánh từng trường, verifyEqual sẽ fail test case nếu không khớp
		WebUI.verifyEqual(uiReceiver, excelReceiver, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyEqual(uiPhone, excelPhone, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyEqual(uiEmail, excelEmail, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyEqual(uiCreateDate, excelCreateDate, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyEqual(uiTotalPrice, excelTotalPrice, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyEqual(uiStatus, excelStatus, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyEqual(uiPaymentMethod, excelPaymentMethod, FailureHandling.CONTINUE_ON_FAILURE)
		}
		SessionHelper.endLoggedInSessionCleanly()
	
