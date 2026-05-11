import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.sql.Timestamp

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utils.*

	String BR = 'Object Repository/User/Auth/'
	String REQ = 'Trường này không được để trống.'

	String excelPath = 'Data Files/Data.xlsx'
	String sheetName = 'Register_Unsuccess'
	dataFileUtil util = new dataFileUtil()

	// Tạo email base unique cho các case validation
	Timestamp now = new Timestamp(System.currentTimeMillis())
	String email = "unsucc_user_" + now.getTime() + "@example.com"
	util.setData(excelPath, "email", email, sheetName)
	
	String name = util.ensureKeyWithDefault(excelPath, "name", "Lao Thanh Thao", sheetName)
	String address = util.ensureKeyWithDefault(excelPath, "address", "Ha Noi", sheetName)
	String phone = util.ensureKeyWithDefault(excelPath, "phone", "0987654321", sheetName)
	String password = util.ensureKeyWithDefault(excelPath, "password", String.valueOf(GlobalVariable.General_Password), sheetName)

	new openLoginPage().navigateToLogin()
	generalFunction fc = new generalFunction()

	WebUI.navigateToUrl(GlobalVariable.Login_url)

	WebUI.delay(2)
	WebUI.waitForElementVisible(findTestObject(BR + 'btn_forget_pass'), 15)
	WebUI.click(findTestObject(BR + 'btn_forget_pass'))
	WebUI.waitForPageLoad(15)
	fc.scrollDown()
	'1. De trong tat ca cac truong — thông báo dưới từng ô (register.html)'
	fc.scrollDown()
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(1)
	WebUI.waitForElementVisible(findTestObject(BR + 'register_error_email'), 5)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_email'), REQ)
	
	'2. Nhập dấu cách tất cả các trường — coi như trống sau trim'
	fc.scrollUp()
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/register')
	WebUI.waitForPageLoad(15)
	WebUI.setText(findTestObject(BR + 'register_email'), " ")
	WebUI.setText(findTestObject(BR + 'register_name'), " ")
	WebUI.setText(findTestObject(BR + 'register_address'), " ")
	WebUI.setText(findTestObject(BR + 'register_phone_number'), " ")
	 
	WebUI.setText(findTestObject(BR + 'register_password'), " ")
	WebUI.setText(findTestObject(BR + 'register_re_password'), " ")
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(1)
	WebUI.waitForElementVisible(findTestObject(BR + 'register_error_email'), 5)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_email'), REQ)
	
	'3. Bỏ trống email'
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/register')
	WebUI.waitForPageLoad(15)
	WebUI.clearText(findTestObject(BR + 'register_email'))
	WebUI.setText(findTestObject(BR + 'register_name'), name)
	WebUI.setText(findTestObject(BR + 'register_address'), address)
	WebUI.setText(findTestObject(BR + 'register_phone_number'), phone)
	WebUI.setText(findTestObject(BR + 'register_password'), password)
	WebUI.setText(findTestObject(BR + 'register_re_password'), password)
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(1)
	WebUI.waitForElementVisible(findTestObject(BR + 'register_error_email'), 5)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_email'), REQ)
	
	'4. Bỏ trống tên'
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/register')
	WebUI.waitForPageLoad(15)
	WebUI.setText(findTestObject(BR + 'register_email'), email)
	WebUI.clearText(findTestObject(BR + 'register_name'))
	WebUI.setText(findTestObject(BR + 'register_address'), address)
	WebUI.setText(findTestObject(BR + 'register_phone_number'), phone)
	WebUI.setText(findTestObject(BR + 'register_password'), password)
	WebUI.setText(findTestObject(BR + 'register_re_password'), password)
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(1)
	WebUI.waitForElementVisible(findTestObject(BR + 'register_error_fullName'), 5)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_fullName'), REQ)
	
	'5. Bỏ trống địa chỉ'
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/register')
	WebUI.waitForPageLoad(15)
	WebUI.setText(findTestObject(BR + 'register_email'), email)
	WebUI.setText(findTestObject(BR + 'register_name'), name)
	WebUI.clearText(findTestObject(BR + 'register_address'))
	WebUI.setText(findTestObject(BR + 'register_phone_number'), phone)
	WebUI.setText(findTestObject(BR + 'register_password'), password)
	WebUI.setText(findTestObject(BR + 'register_re_password'), password)
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(1)
	WebUI.waitForElementVisible(findTestObject(BR + 'register_error_address'), 5)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_address'), REQ)
	
	'6. Bỏ trống SDT'
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/register')
	WebUI.waitForPageLoad(15)
	WebUI.setText(findTestObject(BR + 'register_email'), email)
	WebUI.setText(findTestObject(BR + 'register_name'), name)
	WebUI.setText(findTestObject(BR + 'register_address'), address)
	WebUI.clearText(findTestObject(BR + 'register_phone_number'))
	WebUI.setText(findTestObject(BR + 'register_password'), password)
	WebUI.setText(findTestObject(BR + 'register_re_password'), password)
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(1)
	WebUI.waitForElementVisible(findTestObject(BR + 'register_error_phone'), 5)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_phone'), REQ)
	
	'7. Bỏ trống password'
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/register')
	WebUI.waitForPageLoad(15)
	WebUI.setText(findTestObject(BR + 'register_email'), email)
	WebUI.setText(findTestObject(BR + 'register_name'), name)
	WebUI.setText(findTestObject(BR + 'register_address'), address)
	WebUI.setText(findTestObject(BR + 'register_phone_number'), phone)
	WebUI.clearText(findTestObject(BR + 'register_password'))
	WebUI.clearText(findTestObject(BR + 'register_re_password'))
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(1)
	WebUI.waitForElementVisible(findTestObject(BR + 'register_error_password'), 5)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_password'), REQ)
	WebUI.verifyElementText(findTestObject(BR + 'register_error_confirmPassword'), REQ)
	
	'8. Nhập email đã đăng ký'
	WebUI.navigateToUrl(GlobalVariable.HomePage_url + '/register')
	WebUI.waitForPageLoad(15)
	WebUI.setText(findTestObject(BR + 'register_email'), String.valueOf(GlobalVariable.User_Email))
	WebUI.setText(findTestObject(BR + 'register_name'), name)
	WebUI.setText(findTestObject(BR + 'register_address'), address)
	WebUI.setText(findTestObject(BR + 'register_phone_number'), phone)
	WebUI.setText(findTestObject(BR + 'register_password'), password)
	WebUI.setText(findTestObject(BR + 'register_re_password'), password)
	fc.scrollDown()
	WebUI.click(findTestObject(BR + 'btn_register_1'))
	WebUI.delay(2)
	fc.scrollDown()
	WebUI.waitForElementVisible(findTestObject(BR + 'register_msg_duplicate_email'), 10)
	WebUI.verifyElementText(findTestObject(BR + 'register_msg_duplicate_email'), 'Email này đã được sử dụng')

	// Cập nhật email mới (unique) vào sheet Register_Unsuccess — giống LO02_register_successfully
	Timestamp tsEnd = new Timestamp(System.currentTimeMillis())
	String newEmail = 'unsucc_user_' + tsEnd.getTime() + '@example.com'
	util.setData(excelPath, 'email', newEmail, sheetName)
	WebUI.comment('Đã cập nhật email trong Data.xlsx [' + sheetName + ']: ' + newEmail)
