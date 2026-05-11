package utils
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration

class ContinueOnFailureListener {

    @AfterTestCase
    def continueOnFailure(TestCaseContext testCaseContext) {
        if (testCaseContext.getTestCaseStatus() == 'FAILED') {
            KeywordUtil.markWarning("⚠️ Test Case thất bại nhưng vẫn tiếp tục chạy Suite.")
            try {
                String proj = RunConfiguration.getProjectDir()
                String tsName = testCaseContext.getTestSuiteId() ?: 'Suite'
                String tcName = testCaseContext.getTestCaseId().replaceAll('[^a-zA-Z0-9_\\-]', '_')
                String path = new File(proj, "Reports/screenshots/${tsName}").getAbsolutePath()
                new File(path).mkdirs()
                WebUI.takeScreenshot(path + "/" + tcName + "_FAILED.png")
            } catch (Exception ignore) {}
        }
    }
}
