import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import utils.openShopPage
import utils.dataFileUtil

/** Đọc mô tả (SpeechSynthesis) — bấm nút Đọc mô tả đầu tiên. */
new openShopPage().navigateToShop()

dataFileUtil util = new dataFileUtil()

String SP = 'Object Repository/User/Shop/'
WebUI.waitForElementVisible(findTestObject(SP + 'shop_product_title_first'), 20)
WebUI.mouseOver(findTestObject(SP + 'shop_product_title_first'))
WebUI.waitForElementVisible(findTestObject(SP + 'shop_btn_read_desc_first'), 20)
WebUI.click(findTestObject(SP + 'shop_btn_read_desc_first'))
WebUI.delay(2)
WebUI.comment('PS11 OK — đã kích hoạt đọc mô tả (trình duyệt hỗ trợ speechSynthesis)')

try {
    util.updateExcelTestResult('e:/Support_DoAn/LaoThanhThao/Source/TestCase_DoAn_LaoThanhThao.xlsx', 'Tìm kiếm', 'Nút đọc mô tả sản phẩm', 'Pass', 'Pass')
} catch (Exception e) {
    println('Error writing test result')
}
