package com.mapbox.navigation.testing.ui.rules

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


open class BaseTestRule<A : AppCompatActivity>(activityClass: Class<A>) :
    TestRule {

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<A>
    val ruleChain: RuleChain

    val activity: A
        get() = activityRule.activity

    init {
        activityRule = IntentsTestRule(activityClass, true, true)
        ruleChain = RuleChain.outerRule(activityRule).around(ActiveScreenRule(activityRule))
    }

    fun runOnUiThread(runnable: Runnable?) {
        activityRule.runOnUiThread(runnable)
    }

    override fun apply(statement: Statement, description: Description): Statement {
        return ruleChain.apply(statement, description)
    }

    protected fun onView(viewMatcher: Matcher<View>): ViewInteraction {
        return Espresso.onView(viewMatcher)
    }

    protected fun click(): ViewAction {
        return ViewActions.click()
    }

    protected fun typeText(text: String) {
        ViewActions.typeText(text)
    }

    protected fun closeSoftKeyboard() {
        ViewActions.closeSoftKeyboard()
    }

    protected fun matches(viewMatcher: Matcher<View>): ViewAssertion {
        return ViewAssertions.matches(viewMatcher)
    }

    protected fun withId(id: Int): Matcher<View> {
        return ViewMatchers.withId(id)
    }

    protected fun withText(text: String): Matcher<View> {
        return ViewMatchers.withText(text)
    }

    protected fun onViewWithId(id: Int): ViewInteraction {
        return Espresso.onView(withId(id))
    }

    protected fun onViewWithText(text: String): ViewInteraction {
        return Espresso.onView(ViewMatchers.withText(text))
    }

    protected fun ViewInteraction.click(): ViewInteraction {
        return perform(this@BaseTestRule.click())
    }

    protected fun ViewInteraction.checkText(text: String): ViewInteraction {
        return check(matches(withText(text)))
    }

    protected fun ViewInteraction.checkText(resId: Int): ViewInteraction {
        return this.checkText(activity.getString(resId))
    }

    protected fun getResourceId(s: String): Int {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val packageName = targetContext.packageName
        return targetContext.resources.getIdentifier(s, "id", packageName)
    }
}
