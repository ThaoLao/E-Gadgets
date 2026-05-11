import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
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
	
	WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/OrderManagement/Table_Order_List'))
	WebUI.verifyElementText(findTestObject('Object Repository/Admin/OrderManagement/Order_Management_lbl'),"Danh Sách Đơn Hàng" )
	
	// Hàm tạo TestObject thẻ <li> của số trang (để kiểm tra class active)
	TestObject getPageItem(int pageNum) {
		String xpath = "//ul[@class='pagination']/li[a[text()='" + pageNum + "']]"
		TestObject to = new TestObject("PageItem_" + pageNum)
		to.addProperty("xpath", ConditionType.EQUALS, xpath)
		return to
	}
	
	// Hàm tạo TestObject thẻ <a> số trang (để click chuyển trang)
	TestObject getPageLink(int pageNum) {
		String xpath = "//ul[@class='pagination']/li[a[text()='" + pageNum + "']]/a"
		TestObject to = new TestObject("PageLink_" + pageNum)
		to.addProperty("xpath", ConditionType.EQUALS, xpath)
		return to
	}
	
	// Hàm kiểm tra active dựa trên class thẻ <li>
	def isActive(TestObject pageItem) {
		String classAttr = WebUI.getAttribute(pageItem, 'class')
		WebUI.comment("Page item " + pageItem.getObjectId() + " class: " + classAttr)
		return classAttr != null && classAttr.toLowerCase().contains('active')
	}
	
	// Kiểm tra trang 1 active lúc đầu
	TestObject page1Item = getPageItem(1)
	TestObject page2Item = getPageItem(2)
	TestObject page3Item = getPageItem(3)
	
	assert isActive(page1Item) == true
	assert isActive(page2Item) == false
	assert isActive(page3Item) == false
	
	// Chuyển sang trang 2
	WebUI.click(getPageLink(2))
	WebUI.delay(2)
	
	// Kiểm tra trang 2 active, trang 1 không active
	assert isActive(page2Item) == true
	assert isActive(page1Item) == false
	
	// Chuyển sang trang 3
	WebUI.click(getPageLink(3))
	WebUI.delay(2)
	
	// Kiểm tra trang 3 active, trang 2 không active
	assert isActive(page3Item) == true
	assert isActive(page2Item) == false
	
	SessionHelper.endLoggedInSessionCleanly()
		
	