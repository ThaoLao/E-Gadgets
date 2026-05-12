import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utils.openLoginPage

/**
 * Automation theo sheet "Đăng nhập" — TestCase_DoAn_LaoThanhThao.xlsx
 * UI: login.html (trước đây testcase này cover register; hiện map sang đăng nhập đầy đủ).
 */
String BR = 'Object Repository/User/Auth/'

String REQ = 'Trường này không được để trống.'

def reloadLoginPage = {
	WebUI.navigateToUrl(GlobalVariable.Login_url)
	WebUI.waitForPageLoad(20)
	WebUI.waitForElementVisible(findTestObject(BR + 'Email'), 15)
}

def logoutIfPossible = {
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/logout')
	WebUI.waitForPageLoad(20)
}

def setLoginForm = { email, password ->
	if (email != null) {
		WebUI.clearText(findTestObject(BR + 'Email'))
		WebUI.setText(findTestObject(BR + 'Email'), email)
	}
	if (password != null) {
		WebUI.clearText(findTestObject(BR + 'Password'))
		WebUI.setText(findTestObject(BR + 'Password'), password)
	}
}

def submitLogin = {
	WebUI.click(findTestObject(BR + 'btn_login'))
	WebUI.delay(1)
}

new openLoginPage().navigateToLogin()

// --- TC1 (Excel ID 1): Đăng nhập thành công ---
WebUI.comment('TC Đăng nhập — Đăng nhập thành công')
setLoginForm(String.valueOf(GlobalVariable.User_Email), String.valueOf(GlobalVariable.General_Password))
submitLogin()
WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/icon_Accounts'), 20)
logoutIfPossible()
reloadLoginPage()

// --- TC2 (Excel ID 2): Bỏ trống Email và Mật khẩu ---
WebUI.comment('TC Đăng nhập — Bỏ trống trường')
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_error_email'), 5)
WebUI.verifyElementText(findTestObject(BR + 'login_error_email'), REQ)
WebUI.verifyElementText(findTestObject(BR + 'login_error_password'), REQ)

// --- TC3 (Excel ID 3): Sai định dạng Email ---
WebUI.comment('TC Đăng nhập — Sai định dạng Email')
reloadLoginPage()
setLoginForm('testemail.com', String.valueOf(GlobalVariable.General_Password))
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_error_email'), 5)
WebUI.verifyElementText(findTestObject(BR + 'login_error_email'), 'Email không đúng định dạng')

// --- TC4 (Excel ID 4): Sai mật khẩu ---
WebUI.comment('TC Đăng nhập — Sai mật khẩu')
reloadLoginPage()
setLoginForm(String.valueOf(GlobalVariable.User_Email), 'wrong_password_xxx')
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_msg_bad_credentials'), 15)
WebUI.verifyElementText(findTestObject(BR + 'login_msg_bad_credentials'), 'Email hoặc Mật khẩu không chính xác')

// --- TC5 (Excel ID 5): Tài khoản chưa đăng ký ---
WebUI.comment('TC Đăng nhập — Tài khoản không tồn tại')
reloadLoginPage()
long ts = System.currentTimeMillis()
setLoginForm("not_exist_${ts}@example.com", '123456')
submitLogin()
WebUI.waitForElementVisible(findTestObject(BR + 'login_msg_user_not_found'), 15)
WebUI.verifyElementText(findTestObject(BR + 'login_msg_user_not_found'), 'Tài khoản không tồn tại')

// --- TC7 (Excel ID 7): Mật khẩu dạng ẩn ---
WebUI.comment('TC Đăng nhập — Ô mật khẩu type=password')
reloadLoginPage()
WebUI.verifyElementAttribute(findTestObject(BR + 'Password'), 'type', 'password', 10)

// --- TC8 (Excel ID 8): Chuyển sang Đăng ký ---
WebUI.comment('TC Đăng nhập — Liên kết Đăng ký')
WebUI.waitForElementClickable(findTestObject(BR + 'login_link_register'), 10)
WebUI.click(findTestObject(BR + 'login_link_register'))
WebUI.waitForPageLoad(20)
WebUI.verifyMatch(WebUI.getUrl(), '.*register.*', true)
WebUI.navigateToUrl(GlobalVariable.Login_url)
WebUI.waitForPageLoad(15)

// --- TC6 (Excel ID 6): Trim email + đăng nhập thành công ---
WebUI.comment('TC Đăng nhập — Trim khoảng trắng email')
String em = String.valueOf(GlobalVariable.User_Email)
setLoginForm('  ' + em + '  ', String.valueOf(GlobalVariable.General_Password))
submitLogin()
WebUI.waitForElementVisible(findTestObject('Object Repository/User/HomePage/icon_Accounts'), 20)
logoutIfPossible()

WebUI.comment('Hoàn tất — sheet Đăng nhập (Admin LO03)')
