import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.openShopPage
import utils.dataFileUtil

/** Modal chi tiết — bấm nút quickview, modal #product-modal hiển thị. */
new openShopPage().navigateToShop()

dataFileUtil util = new dataFileUtil()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_product_title_first'), 20)
WebUI.mouseOver(findTestObject(SP + 'shop_product_title_first'))
WebUI.waitForElementVisible(findTestObject(SP + 'shop_btn_quickview_first'), 15)
WebUI.click(findTestObject(SP + 'shop_btn_quickview_first'))
WebUI.waitForElementVisible(findTestObject(SP + 'shop_modal_product'), 15)
WebUI.verifyElementVisible(findTestObject(SP + 'shop_modal_product'))
WebUI.comment('PS08 OK — modal hiển thị')

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Nút chuyển modal chi tiết sản phẩm', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
