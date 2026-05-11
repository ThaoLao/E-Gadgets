import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utils.*

/**
 * Các trường hợp đăng nhập thất bại / validation (sheet Đăng nhập — Excel), UI login.html
 */
String BR = 'Object Repository/User/Auth/'
String REQ = 'Trường này không được để trống.'

String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Login_Unsuccess'
dataFileUtil util = new dataFileUtil()

String email = util.ensureKeyWithDefault(excelPath, 'email', String.valueOf(GlobalVariable.User_Email), sheetName)
String password = util.ensureKeyWithDefault(excelPath, 'password', String.valueOf(GlobalVariable.General_Password), sheetName)
String name = util.ensureKeyWithDefault(excelPath, "name", "Lao Thanh Thao", sheetName)
String address = util.ensureKeyWithDefault(excelPath, "address", "Ha Noi", sheetName)
String phone = util.ensureKeyWithDefault(excelPath, "phone", "0987654321", sheetName)

def reloadLoginPage = {
	WebUI.navigateToUrl(GlobalVariable.Login_url)
	WebUI.waitForPageLoad(20)
	WebUI.waitForElementVisible(findTestObject(BR + 'Email'), 15)
}

def setLoginForm = { e, p ->
	if (e != null) {
		WebUI.clearText(findTestObject(BR + 'Email'))
		WebUI.setText(findTestObject(BR + 'Email'), e)
	}
	if (p != null) {
		WebUI.clearText(findTestObject(BR + 'Password'))
		WebUI.setText(findTestObject(BR + 'Password'), p)
	}
}

def submitLogin = {
	WebUI.click(findTestObject(BR + 'btn_login'))
	WebUI.delay(1)
}

new openLoginPage().navigateToLogin()

// Đảm bảo đăng xuất trước khi thực hiện test case login thất bại
String HP = 'Object Repository/User/HomePage/'
if (WebUI.verifyElementPresent(findTestObject(HP + 'icon_Accounts'), 3, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject(HP + 'icon_Accounts'))
	WebUI.delay(1)
	if (WebUI.verifyElementPresent(findTestObject(HP + 'header_menu_link_logout'), 3, FailureHandling.OPTIONAL)) {
		WebUI.click(findTestObject(HP + 'header_menu_link_logout'))
		WebUI.waitForPageLoad(20)
	}
}
WebUI.navigateToUrl(GlobalVariable.Login_url)

// 1. Để trống Email và Mật khẩu
WebUI.comment('TC — Bỏ trống trường')
reloadLoginPage() // Đảm bảo trang sạch
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_error_email'), 10)
WebUI.verifyElementText(findTestObject(BR + 'login_error_email'), REQ)
WebUI.verifyElementText(findTestObject(BR + 'login_error_password'), REQ)

// 2. Sai định dạng email
WebUI.comment('TC — Email không đúng định dạng')
reloadLoginPage()
setLoginForm('testemail.com', String.valueOf(GlobalVariable.General_Password))
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_error_email'), 10)
WebUI.verifyElementText(findTestObject(BR + 'login_error_email'), 'Email không đúng định dạng')

// 3. Sai mật khẩu
WebUI.comment('TC — Sai mật khẩu')
reloadLoginPage()
setLoginForm(String.valueOf(GlobalVariable.User_Email), 'wrong_pass_xxx')
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_msg_bad_credentials'), 15)
WebUI.verifyElementText(findTestObject(BR + 'login_msg_bad_credentials'), 'Email hoặc Mật khẩu không chính xác')

// 4. Tài khoản không tồn tại
WebUI.comment('TC — Tài khoản không tồn tại')
reloadLoginPage()
long ts = System.currentTimeMillis()
setLoginForm("ghost_${ts}@example.com", '123456')
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_msg_user_not_found'), 15)
WebUI.verifyElementText(findTestObject(BR + 'login_msg_user_not_found'), 'Tài khoản không tồn tại')

// 5. Ô mật khẩu ẩn
WebUI.comment('TC — Hiển thị mật khẩu dạng password')
reloadLoginPage()
WebUI.verifyElementAttributeValue(findTestObject(BR + 'Password'), 'type', 'password', 10)

// 6. Điều hướng Đăng ký
WebUI.comment('TC — Chuyển sang Đăng ký')
WebUI.waitForElementClickable(findTestObject(BR + 'login_link_register'), 10)
WebUI.click(findTestObject(BR + 'login_link_register'))
WebUI.waitForPageLoad(20)
WebUI.verifyMatch(WebUI.getUrl(), '.*register.*', true)

