package com.sportapp_grupo1.utils

import android.os.IBinder
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.TypeSafeMatcher
import org.junit.runner.Description


class CustomAssertions {
    companion object {
        fun hasItemCount(count: Int): ViewAssertion {
            return RecyclerViewItemCountAssertion(count)
        }

        fun greaterItem(count: Int): ViewAssertion {
            return RecyclerViewItemGreaterAssertion(count)
        }
    }

    private class RecyclerViewItemCountAssertion(private val count: Int) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            if (view !is RecyclerView) {
                throw IllegalStateException("The asserted view is not RecyclerView")
            }

            if (view.adapter == null) {
                throw IllegalStateException("No adapter is assigned to RecyclerView")
            }

            ViewMatchers.assertThat(
                "RecyclerView item count",
                view.adapter!!.itemCount,
                CoreMatchers.equalTo(count)
            )
        }
    }

    private class RecyclerViewItemGreaterAssertion(private val count: Int) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            if (view !is RecyclerView) {
                throw IllegalStateException("The asserted view is not RecyclerView")
            }

            if (view.adapter == null) {
                throw IllegalStateException("No adapter is assigned to RecyclerView")
            }

            ViewMatchers.assertThat(
                "RecyclerView check greater",
                (view.adapter!!.itemCount >= count),
                CoreMatchers.equalTo(true)
            )
        }
    }



}