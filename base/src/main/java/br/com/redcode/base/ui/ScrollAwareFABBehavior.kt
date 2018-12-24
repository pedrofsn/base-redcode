package br.com.redcode.base.ui

/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import br.com.redcode.base.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by pedrofsn on 03/02/18.
 */
class ScrollAwareFABBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {
    private var mIsAnimatingOut = false

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
            directTargetChild: View, target: View, nestedScrollAxes: Int
    ): Boolean {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
                coordinatorLayout,
                child,
                directTargetChild,
                target,
                nestedScrollAxes
        )
    }

    override fun onNestedScroll(
            coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
            target: View, dxConsumed: Int, dyConsumed: Int,
            dxUnconsumed: Int, dyUnconsumed: Int
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.visibility == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateIn(child)
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private fun animateOut(button: FloatingActionButton) {
        val anim = AnimationUtils.loadAnimation(button.context, R.anim.fab_out)
        anim.interpolator = INTERPOLATOR
        anim.duration = 200L
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                this@ScrollAwareFABBehavior.mIsAnimatingOut = true
            }

            override fun onAnimationEnd(animation: Animation) {
                this@ScrollAwareFABBehavior.mIsAnimatingOut = false
                button.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        button.startAnimation(anim)
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private fun animateIn(button: FloatingActionButton) {
        button.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(button.context, R.anim.fab_in)
        anim.duration = 200L
        anim.interpolator = INTERPOLATOR
        button.startAnimation(anim)
    }

    companion object {

        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }
}