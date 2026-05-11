import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat
import java.util.Date

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utils.*

/**
 * Đăng nhập thành công — sheet Login_Success (Key | Value) trong Data Files/Data.xlsx.
 * Input: đọc email, password qua dataFileUtil.ensureKeyWithDefault (thiếu thì ghi mặc định từ GlobalVariable).
 * Output: ghi last_login_ok_at sau khi verify menu Đơn hàng (dấu hiệu đăng nhập thành công).
 */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Login_Success'
dataFileUtil util = new dataFileUtil()

String email = util.ensureKeyWithDefault(excelPath, 'email', String.valueOf(GlobalVariable.User_Email), sheetName)
String password = util.ensureKeyWithDefault(excelPath, 'password', String.valueOf(GlobalVariable.General_Password), sheetName)
String name = util.ensureKeyWithDefault(excelPath, "name", "Lao Thanh Thao", sheetName)
String address = util.ensureKeyWithDefault(excelPath, "address", "Ha Noi", sheetName)
String phone = util.ensureKeyWithDefault(excelPath, "phone", "0987654321", sheetName)

new openLoginPage().navigateToLogin()

WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/Email'), 20)
WebUI.waitForElementClickable(findTestObject('Object Repository/User/Auth/Email'), 20)
WebUI.clearText(findTestObject('Object Repository/User/Auth/Email'))
WebUI.setText(findTestObject('Object Repository/User/Auth/Email'), email)

WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/Password'), 20)
WebUI.clearText(findTestObject('Object Repository/User/Auth/Password'))
WebUI.setText(findTestObject('Object Repository/User/Auth/Password'), password)

WebUI.click(findTestObject('Object Repository/User/Auth/btn_login'))

String HP = 'Object Repository/User/HomePage/'
WebUI.waitForElementVisible(findTestObject(HP + 'icon_Accounts'), 20)
WebUI.click(findTestObject(HP + 'icon_Accounts'))
WebUI.delay(1)
WebUI.waitForElementVisible(findTestObject(HP + 'header_menu_link_orders'), 15)
WebUI.verifyElementVisible(findTestObject(HP + 'header_menu_link_orders'))
WebUI.verifyElementText(findTestObject(HP + 'header_menu_link_orders'), 'Đơn Hàng')
WebUI.verifyElementNotPresent(findTestObject(HP + 'header_menu_guest_link_login'), 5)
WebUI.verifyElementNotPresent(findTestObject(HP + 'header_menu_guest_link_register'), 5)

String ts = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(new Date())
util.setData(excelPath, 'last_login_ok_at', ts, sheetName)
WebUI.comment('Đăng nhập OK — Login_Success email: ' + email + ' — ghi last_login_ok_at: ' + ts)

String home = String.valueOf(GlobalVariable.HomePage_url)
WebUI.navigateToUrl(home)

//// Trả trình duyệt về trạng thái guest khi suite/driver được tái sử dụng (bổ sung cho listener).
//SessionHelper.endLoggedInSessionCleanly()
