package com.mapbox.navigation.testing.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiObject2
import com.mapbox.navigation.testing.ui.AppNotificationManager.Companion.NOTIFICATION_MESSAGE
import com.mapbox.navigation.testing.ui.AppNotificationManager.Companion.NOTIFICATION_TITLE
import com.mapbox.navigation.testing.ui.rules.NotificationTestRule
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : NotificationTestRule<MainActivity>(MainActivity::class.java) {

    @Test
    fun testDefaultMessage() {
        onView(withId(R.id.et_message))
            .check(matches(withText(activity.getString(R.string.default_message))))
    }

    @Test
    fun testNewMessage() {
        onViewWithId(R.id.btn_set_text)
            .click()

        onViewWithId(R.id.et_message)
            .checkText(R.string.new_message)
    }

    @Test
    fun testNotification() {
        onViewWithId(R.id.btn_show_notification)
            .click()

        uiDevice.waitForNotification()

        val title: UiObject2 = uiDevice.findObject(By.text(NOTIFICATION_TITLE))
        val message: UiObject2 = uiDevice.findObject(By.textStartsWith(NOTIFICATION_MESSAGE))

        assertEquals(NOTIFICATION_TITLE, title.text)
        assertEquals(NOTIFICATION_MESSAGE, message.text)

        clearAllNotifications()
    }
}
