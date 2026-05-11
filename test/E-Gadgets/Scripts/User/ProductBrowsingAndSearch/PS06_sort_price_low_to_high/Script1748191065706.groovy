import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.dataFileUtil
import utils.openShopPage

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Select
import com.kms.katalon.core.webui.driver.DriverFactory

/** Sắp xếp Giá thấp → cao — sort_option_value = priceLowToHigh */
String excelPath = 'Data Files/Data.xlsx'
String sheetName = 'Tim_Kiem'
dataFileUtil util = new dataFileUtil()

String sortVal = util.ensureKeyWithDefault(excelPath, 'sort_option_value', 'priceLowToHigh', sheetName)

new openShopPage().navigateToShop()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_sort_select'), 20)
WebDriver drv = DriverFactory.getWebDriver()
Select sel = new Select(drv.findElement(org.openqa.selenium.By.id('shop-sort-select')))
sel.selectByValue(sortVal)
WebUI.click(findTestObject(SP + 'shop_btn_search'))
WebUI.waitForPageLoad(25)

WebUI.verifyElementPresent(findTestObject(SP + 'shop_pagination_status'), 10)
WebUI.comment('PS06 OK — đã chọn sắp xếp ' + sortVal)

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Sắp xếp theo tiêu chí', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
