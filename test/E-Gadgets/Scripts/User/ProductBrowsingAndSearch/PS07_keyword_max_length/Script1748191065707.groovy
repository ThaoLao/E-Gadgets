import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory

import utils.dataFileUtil
import utils.openShopPage

/** Giới hạn 501 ký tự — input cắt còn 500 (maxlength + script). */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

String long501 = util.getValue(excelPath, 'long_string_test', sheetName)
if (long501 == null || long501.length() < 501) {
	long501 = ('x' * 501)
	util.setData(excelPath, 'long_string_test', long501, sheetName)
}

new openShopPage().navigateToShop()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_input_keyword'), 20)

WebDriver drv = DriverFactory.getWebDriver()
def el = drv.findElement(org.openqa.selenium.By.id('shop-search-keyword'))
((JavascriptExecutor) drv).executeScript(
		'var e=arguments[0],t=arguments[1]; e.value=t; e.dispatchEvent(new Event("input",{bubbles:true}));', el, long501)

String v = el.getAttribute('value')
WebUI.verifyEqual(v.length() <= 500, true)
WebUI.comment('PS07 OK — độ dài sau giới hạn: ' + v.length())

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Kiểm tra giới hạn ký tự', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
