import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import utils.*

	WebUI.callTestCase(
		findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
		[('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	
	generalFunction fc = new generalFunction()
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Post'))
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Post_Management'))
	WebUI.delay(2)
	
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Add_Post'))
	
	// Nhập tiêu đề
	WebUI.setText(findTestObject('Object Repository/Admin/PostManagement/Input_BlogTitle'), 'AI vs Chip War – Who Wins?')
	

	WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/PostManagement/Input_UserPost'), 'Thanh Huong', false)
	
	// Nhập tóm tắt
	WebUI.setText(findTestObject('Object Repository/Admin/PostManagement/Input_Summary'), 'A short comparison between AI and Chip War')
	
	// Nhập nội dung
	WebUI.setText(findTestObject('Object Repository/Admin/PostManagement/Input_Content'), 'In this article, we analyze the strengths and weaknesses of AI development vs chip manufacturing...')
	fc.scrollDown()
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Submit'))
	WebUI.delay(2)
	
	// Kiểm tra thông báo
	WebUI.verifyElementText(findTestObject('Object Repository/Admin/PostManagement/Alert_Success'), 'Thêm bài viết thành công!')
	
	SessionHelper.endLoggedInSessionCleanly()