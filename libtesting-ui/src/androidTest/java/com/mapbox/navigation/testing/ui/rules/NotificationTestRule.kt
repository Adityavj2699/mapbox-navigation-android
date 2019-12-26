package com.mapbox.navigation.testing.ui.rules

import androidx.appcompat.app.AppCompatActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.mapbox.navigation.testing.ui.R

open class NotificationTestRule<A : AppCompatActivity>(activityClass: Class<A>) :
    BaseTestRule<A>(activityClass) {

    companion object {
        private const val TIMEOUT = 3_000L
        private const val CLEAR_ALL_NOTIFICATIONS_RES_ID = "com.android.systemui:id/dismiss_text"
    }

    val appName: String by lazy { activity.getString(R.string.app_name) }
    val uiDevice: UiDevice by lazy { UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) }

    protected fun clearAllNotifications() {
        uiDevice.openNotification()
        uiDevice.wait(Until.hasObject(By.textStartsWith(appName)), TIMEOUT)
        val clearAll: UiObject2 = uiDevice.findObject(By.res(CLEAR_ALL_NOTIFICATIONS_RES_ID))
        clearAll.click()
    }

    protected fun UiDevice.waitForNotification() {
        this.openNotification()
        this.wait(Until.hasObject(By.textStartsWith(appName)), TIMEOUT)
    }
}
