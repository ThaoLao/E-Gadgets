import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat
import java.util.Date

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utils.dataFileUtil
import utils.openLoginPage
import utils.openShopPage
import utils.SessionHelper

/**
 * Thêm giỏ khi đã đăng nhập — Login_Success; kỳ vọng alert đúng nội dung thành công.
 */
String excelPath = 'Data Files/Data.xlsx'
String sheetLogin = 'Login_Success'
String sheetShop = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

String email = util.ensureKeyWithDefault(excelPath, 'email', String.valueOf(GlobalVariable.User_Email), sheetLogin)
String password = util.ensureKeyWithDefault(excelPath, 'password', String.valueOf(GlobalVariable.General_Password), sheetLogin)

try {
	new openLoginPage().navigateToLogin()
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/Email'), 20)
	WebUI.clearText(findTestObject('Object Repository/User/Auth/Email'))
	WebUI.setText(findTestObject('Object Repository/User/Auth/Email'), email)
	WebUI.clearText(findTestObject('Object Repository/User/Auth/Password'))
	WebUI.setText(findTestObject('Object Repository/User/Auth/Password'), password)
	WebUI.click(findTestObject('Object Repository/User/Auth/btn_login'))
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/icon_Accounts'), 20)

	new openShopPage().navigateToShop()

	TestObject addFirst = new TestObject('shop_add_cart_first')
	addFirst.addProperty('xpath', ConditionType.EQUALS, "(//a[contains(@class,'shop-btn-add-cart')])[1]")

	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Shop/shop_product_title_first'), 20)
	WebUI.mouseOver(findTestObject('Object Repository/User/Shop/shop_product_title_first'))
	WebUI.waitForElementVisible(addFirst, 20)
	WebUI.click(addFirst)
	WebUI.waitForAlert(15)
	String al = WebUI.getAlertText()
	WebUI.acceptAlert()
	WebUI.verifyEqual(al.contains('Thêm sản phẩm vào giỏ hàng thành công'), true)

	String ts = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(new Date())
	util.setData(excelPath, 'last_cart_add_ok_at', ts, sheetShop)
	WebUI.comment('PS10 OK — ' + ts)

	try {
		util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Nút thêm vào giỏ hàng', 'Pass', 'Pass')
	} catch (Exception e) {
		println('Error writing test result')
	}
} finally {
	SessionHelper.endLoggedInSessionCleanly()
}
