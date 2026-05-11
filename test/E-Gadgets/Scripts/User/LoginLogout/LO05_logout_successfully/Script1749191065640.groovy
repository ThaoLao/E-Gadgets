import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utils.*

/**
 * Đăng xuất: sau khi đăng nhập, trong setting__menu có Đăng xuất;
 * bấm Đăng xuất → về trang chủ (/) → menu hiện Đăng nhập / Đăng ký, không còn Đơn hàng / Đăng xuất.
 */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Login_Success'
dataFileUtil util = new dataFileUtil()

String email = util.ensureKeyWithDefault(excelPath, 'email', String.valueOf(GlobalVariable.User_Email), sheetName)
String password = util.ensureKeyWithDefault(excelPath, 'password', String.valueOf(GlobalVariable.General_Password), sheetName)
String name = util.ensureKeyWithDefault(excelPath, "name", "Lao Thanh Thao", sheetName)
String address = util.ensureKeyWithDefault(excelPath, "address", "Ha Noi", sheetName)
String phone = util.ensureKeyWithDefault(excelPath, "phone", "0987654321", sheetName)

String HP = 'Object Repository/User/HomePage/'
String AP = 'Object Repository/User/Auth/'

new openLoginPage().navigateToLogin()

WebUI.waitForElementVisible(findTestObject(AP + 'Email'), 20)
WebUI.clearText(findTestObject(AP + 'Email'))
WebUI.setText(findTestObject(AP + 'Email'), email)
WebUI.clearText(findTestObject(AP + 'Password'))
WebUI.setText(findTestObject(AP + 'Password'), password)
WebUI.click(findTestObject(AP + 'btn_login'))

WebUI.waitForElementVisible(findTestObject(HP + 'icon_Accounts'), 20)
WebUI.click(findTestObject(HP + 'icon_Accounts'))
WebUI.delay(1)

WebUI.waitForElementVisible(findTestObject(HP + 'header_menu_link_logout'), 15)
WebUI.verifyElementVisible(findTestObject(HP + 'header_menu_link_logout'))
WebUI.verifyElementText(findTestObject(HP + 'header_menu_link_logout'), 'Đăng Xuất')

WebUI.click(findTestObject(HP + 'header_menu_link_logout'))
WebUI.waitForPageLoad(25)

String home = String.valueOf(GlobalVariable.HomePage_url).replaceAll('/$', '')
String afterUrl = WebUI.getUrl()
WebUI.verifyEqual(afterUrl.startsWith(home), true)
WebUI.comment('URL sau đăng xuất: ' + afterUrl)

WebUI.waitForElementVisible(findTestObject(HP + 'icon_Accounts'), 20)
WebUI.click(findTestObject(HP + 'icon_Accounts'))
WebUI.delay(1)

WebUI.waitForElementVisible(findTestObject(HP + 'header_menu_guest_link_login'), 15)
WebUI.verifyElementVisible(findTestObject(HP + 'header_menu_guest_link_login'))
WebUI.verifyElementVisible(findTestObject(HP + 'header_menu_guest_link_register'))
WebUI.verifyElementText(findTestObject(HP + 'header_menu_guest_link_login'), 'Đăng Nhập')
WebUI.verifyElementText(findTestObject(HP + 'header_menu_guest_link_register'), 'Đăng Ký')

WebUI.verifyElementNotPresent(findTestObject(HP + 'header_menu_link_logout'), 5)
WebUI.verifyElementNotPresent(findTestObject(HP + 'header_menu_link_orders'), 5)

WebUI.comment('Đăng xuất OK — quay về trang chủ, menu guest hiển thị Đăng nhập / Đăng ký')
