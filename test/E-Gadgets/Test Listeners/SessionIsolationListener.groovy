import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.context.TestCaseContext

import utils.SessionHelper

/**
 * Clears cookies after each Web UI test case when the driver is still open (e.g. reuse driver, local runs).
 * Enable in Katalon: Project → Settings → Execution → select this listener for default / suite, or add to suite options.
 */
class SessionIsolationListener {

	@AfterTestCase
	void afterEachTestCase(TestCaseContext testCaseContext) {
		try {
			if (SessionHelper.hasWebDriver()) {
				SessionHelper.resetWebUiSessionForNextTest()
			}
		} catch (Throwable ignored) {
		}
	}
}
