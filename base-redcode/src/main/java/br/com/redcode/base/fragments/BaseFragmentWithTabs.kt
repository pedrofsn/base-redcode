package br.com.redcode.base.fragments

import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import br.com.redcode.base.R
import br.com.redcode.base.extensions.gone
import br.com.redcode.base.extensions.visible
import br.com.redcode.base.interfaces.FragmentTitleController
import com.google.android.material.tabs.TabLayout

/**
 * Created by pedrofsn on 03/02/18.
 */
abstract class BaseFragmentWithTabs : BaseFragment() {

    lateinit var adapter: PagerAdapter
    lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override val layout: Int = R.layout.activity_with_tabs
    abstract val countTabs: Int

    override fun middleOnCreate() {
        viewPager.offscreenPageLimit = countTabs
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initView(view: View?) {
        super.initView(view)

        view?.let {
            tabLayout = view.findViewById(R.id.tabs)
            viewPager = view.findViewById(R.id.viewpager)
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        viewPager.visible()
        tabLayout.visible()
    }

    override fun showProgress() {
        super.showProgress()
        viewPager.gone()
        tabLayout.gone()
    }

    abstract fun setupViewPager(viewPager: ViewPager)

    fun addFragment(fragment: BaseFragment, titulo: String) {
        if (titulo.isBlank()) {
            throw RuntimeException("Aba sem título!")
        }

        (adapter as? FragmentTitleController)?.addFragment(fragment, titulo)
        adapter.notifyDataSetChanged()
    }
}