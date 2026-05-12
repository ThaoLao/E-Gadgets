import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import utils.*

	WebUI.callTestCase(
		findTestCase('Test Cases/User/LoginLogout/LO04_login_successfully'),
		[('username') : GlobalVariable.Admin_Email, ('password') : GlobalVariable.General_Password]
	)
	
	generalFunction fc = new generalFunction()
	dataFileUtil util = new dataFileUtil()
	String excelPath = 'Data Files/Data.xlsx'
	String PostTitle1 = util.getData(excelPath, "PostTitle1")
	String PostTitle2 = util.getData(excelPath, "PostTitle2")
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_Accounts'))
	
	WebUI.verifyElementVisible(findTestObject('Object Repository/User/HomePage/icon_admin'))
	
	WebUI.click(findTestObject('Object Repository/User/HomePage/icon_admin'))
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/Admin/PostManagement/Button_Post'))
	WebUI.delay(2)
	
	
	'1. Search full text'
	TestObject searchInput = findTestObject('Object Repository/Admin/PostManagement/input_Keyword')
	WebUI.setText(searchInput, PostTitle1)
	WebUI.sendKeys(searchInput, org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.ENTER))
	
	WebUI.delay(2)
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title'), 5, FailureHandling.OPTIONAL)) {
		String actualTitle = WebUI.getText(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title')).trim()
		WebUI.verifyMatch(actualTitle, PostTitle1, false)
	} else {
		return false
	}
	
	 searchInput = findTestObject('Object Repository/Admin/PostManagement/input_Keyword')
	WebUI.setText(searchInput, PostTitle2)
	WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
	WebUI.delay(2)
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title'), 5, FailureHandling.OPTIONAL)) {
		String actualTitle2 = WebUI.getText(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title')).trim()
		WebUI.verifyMatch(actualTitle2, PostTitle2, false)
	} else {
		return false
	}
	
	
	'2. Search half text'
	int halfLength = (int)(PostTitle1.length() / 2)
	String partialName = PostTitle1.length() > 3 ? PostTitle1.substring(0, halfLength) : PostTitle1
	searchInput = findTestObject('Object Repository/Admin/PostManagement/input_Keyword')
	WebUI.setText(searchInput, partialName)
	WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
	WebUI.delay(2)
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title'), 5, FailureHandling.OPTIONAL)) {
	    String actualName = WebUI.getText(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title')).trim()
	    WebUI.verifyMatch(actualName.toLowerCase(), PostTitle1.toLowerCase(), true)
	} else {
		return false
	}
	
	int halfLength1 = (int)(PostTitle2.length() / 2)
	String partialName1 = PostTitle2.length() > 3 ? PostTitle2.substring(0, halfLength1) : PostTitle2
	searchInput = findTestObject('Object Repository/Admin/PostManagement/input_Keyword')
	WebUI.setText(searchInput, partialName1)
	WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
	WebUI.delay(2)
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title'), 5, FailureHandling.OPTIONAL)) {
		String actualName2 = WebUI.getText(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title')).trim()
		WebUI.verifyMatch(actualName2.toLowerCase(), PostTitle2.toLowerCase(), true)
	} else {
		return false
	}
	

	'3. Search with whitespace'
	String spaceKeyword = "  " + PostTitle1 + "  "
	searchInput = findTestObject('Object Repository/Admin/PostManagement/input_Keyword')
	WebUI.setText(searchInput, spaceKeyword)
	WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
	WebUI.delay(2)
	
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title'), 5, FailureHandling.OPTIONAL)) {
		String actualName = WebUI.getText(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title')).trim()
		WebUI.verifyMatch(actualName, PostTitle1, false)
	} else {
		return false

	}
	
	'4. Search empty keyword'
	searchInput = findTestObject('Object Repository/Admin/PostManagement/input_Keyword')
	WebUI.setText(searchInput, "")
	WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
	WebUI.delay(2)
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title'), 5, FailureHandling.OPTIONAL)) {
		return true
	} else {
		return false
	}
	
	'5. Search với tên sách không tồn tại'
	String fakeName = PostTitle1 + "_NotExist123"
	searchInput = findTestObject('Object Repository/Admin/PostManagement/input_Keyword')
	WebUI.setText(searchInput, fakeName)
	WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
	WebUI.delay(2)
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/PostManagement/List_Post_Title'), 5, FailureHandling.OPTIONAL)) {
		return true
	} else {
		return false
	}
	
	SessionHelper.endLoggedInSessionCleanly()
	


	