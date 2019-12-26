package com.mapbox.navigation.testing.ui.rules

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.test.rule.ActivityTestRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


internal open class ActiveScreenRule<A : AppCompatActivity>(
    protected val activityRule: ActivityTestRule<A>
) : TestRule {

    override fun apply(statement: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                activityRule.runOnUiThread {
                    activityRule
                        .activity?.window?.addFlags(
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                    )
                }
                statement.evaluate()
            }
        }
    }
}
