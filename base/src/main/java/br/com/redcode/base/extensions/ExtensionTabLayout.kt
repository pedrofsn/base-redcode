package br.com.redcode.base.extensions

import com.google.android.material.tabs.TabLayout


fun TabLayout.onTabChanged(callback: (Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let { callback.invoke(it.position) }
        }
    })
}