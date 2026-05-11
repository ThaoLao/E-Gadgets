import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.sql.Timestamp

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utils.*

	new openLoginPage().navigateToLogin()
	generalFunction fc = new generalFunction()
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	String sheetName = 'Register_Success'

	WebUI.navigateToUrl(GlobalVariable.Login_url)

	// Tạo email unique ngay từ đầu và lưu vào Excel để tránh dùng lại email cũ khi test crash
	Timestamp now = new Timestamp(System.currentTimeMillis())
	String email = "user_" + now.getTime() + "@example.com"
	util.setData(excelPath, "email", email, sheetName)
	
	String name = util.ensureKeyWithDefault(excelPath, "name", "Lao Thanh Thao", sheetName)
	String address = util.ensureKeyWithDefault(excelPath, "address", "Ha Noi", sheetName)
	String phone = util.ensureKeyWithDefault(excelPath, "phone", "0987654321", sheetName)
	String password = util.ensureKeyWithDefault(excelPath, "password", String.valueOf(GlobalVariable.General_Password), sheetName)

	String HP = 'Object Repository/User/HomePage/'

	WebUI.delay(2)
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/btn_forget_pass'), 15)
	WebUI.click(findTestObject('Object Repository/User/Auth/btn_forget_pass'))
	WebUI.waitForPageLoad(15)
	fc.scrollDown()

	// Điền form
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/register_email'), 15)
	WebUI.setText(findTestObject('Object Repository/User/Auth/register_email'), email)
	WebUI.setText(findTestObject('Object Repository/User/Auth/register_name'), name)
	WebUI.setText(findTestObject('Object Repository/User/Auth/register_address'), address)
	WebUI.setText(findTestObject('Object Repository/User/Auth/register_phone_number'), phone)
	WebUI.setText(findTestObject('Object Repository/User/Auth/register_password'), password)
	WebUI.setText(findTestObject('Object Repository/User/Auth/register_re_password'), password)
	
	fc.scrollDown()
	WebUI.click(findTestObject('Object Repository/User/Auth/btn_register_1'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/msg_result_register'), 15)
	WebUI.verifyElementText(findTestObject('Object Repository/User/Auth/msg_result_register'), "Đăng ký thành công!")
	WebUI.comment("Registration successful with: " + email)
	
	WebUI.delay(2)
	WebUI.navigateToUrl(GlobalVariable.Login_url)
	WebUI.waitForElementVisible(findTestObject('Object Repository/User/Auth/Email'), 20)
	
	// Thao tác login với user vừa tạo
	WebUI.setText(findTestObject('Object Repository/User/Auth/Email'), email)
	WebUI.setText(findTestObject('Object Repository/User/Auth/Password'), password)
	WebUI.click(findTestObject('Object Repository/User/Auth/btn_login'))
	
	WebUI.delay(2)
	WebUI.waitForElementVisible(findTestObject(HP + 'icon_Accounts'), 20)
	WebUI.click(findTestObject(HP + 'icon_Accounts'))
	WebUI.delay(1)
	WebUI.verifyElementVisible(findTestObject(HP + 'header_menu_link_orders'))
	
	WebUI.closeBrowser()

