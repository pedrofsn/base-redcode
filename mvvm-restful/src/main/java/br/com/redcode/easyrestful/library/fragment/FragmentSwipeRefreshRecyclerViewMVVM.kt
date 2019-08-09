package br.com.redcode.easyrestful.library.fragment

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.extensions.getString
import br.com.redcode.base.extensions.handleEnterKeyboard
import br.com.redcode.easyrecyclerview.library.adapter.RecyclerViewEmptySupport
import br.com.redcode.easyrestful.library.R
import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModel

/**
 * Created by pedrofsn on 03/02/18.
 */
abstract class FragmentSwipeRefreshRecyclerViewMVVM<B : ViewDataBinding, VM : BaseViewModel> : FragmentMVVM<B, VM>() {

    override val layout: Int = R.layout.ui_swiperefresh_recyclerview

    private val swipeRefreshLayout: SwipeRefreshLayout? by lazy { binding.root.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout) }
    val recyclerView: RecyclerViewEmptySupport? by lazy { binding.root.findViewById<RecyclerViewEmptySupport>(R.id.recyclerView) }
    private val textViewEmpty: TextView? by lazy { binding.root.findViewById<TextView>(R.id.textViewEmpty) }

    private var searchItem: MenuItem? = null
    var searchPlate: EditText? = null
    open var query: String? = null

    abstract val colummns: Int
    abstract val hasSearch: Boolean
    abstract val hasSwipeRefreshLayout: Boolean
    abstract val adapter: RecyclerView.Adapter<*>

    open val layoutManager: RecyclerView.LayoutManager by lazy { StaggeredGridLayoutManager(colummns, StaggeredGridLayoutManager.VERTICAL) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (hasSearch) {
            setHasOptionsMenu(true)
        }
    }

    override fun middleOnCreate() {
        super.middleOnCreate()
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.setEmptyView(textViewEmpty)

        enablePullToRefresh()
    }

    private fun enablePullToRefresh() {
        swipeRefreshLayout?.apply {
            if (hasSwipeRefreshLayout) {
                setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
                setOnRefreshListener { onRefreshed() }
                isEnabled = true
            } else {
                isEnabled = false
            }
        }
    }

    open fun onRefreshed() {
        // DO YOUR LOGIC
    }

    override fun showProgress() {
        if (hasSwipeRefreshLayout) {
            (activity as BaseActivity).processing = true
            swipeRefreshLayout?.let { it.isRefreshing = true }
        } else {
            super.showProgress()
        }
    }


    override fun hideProgress() {
        if (hasSwipeRefreshLayout) {
            (activity as BaseActivity).processing = false
            swipeRefreshLayout?.let { it.isRefreshing = false }
        } else {
            super.hideProgress()
        }

        recyclerView?.showHideEmpty()
    }

    open fun getOptionsMenu() = R.menu.menu_search

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (hasSearch) {
            inflater?.inflate(getOptionsMenu(), menu)

            searchItem = menu?.findItem(R.id.menuItemSearch)

            val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager

            val searchView = searchItem?.actionView as SearchView
            searchView.setSearchableInfo(searchManager?.getSearchableInfo((context as Activity).componentName))
            searchPlate = searchView.findViewById(R.id.search_src_text)
            searchPlate?.hint = getString(R.string.search)
            searchEmptyQuery()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchEmptyQuery() {
        if (hasSearch) searchPlate?.handleEnterKeyboard { search() }
    }

    open fun search(query: String? = searchPlate?.getString()) {
        hideKeyboard(searchPlate)
        this.query = query
    }


    private fun hideKeyboard(editText: EditText?) {
        editText?.apply {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

}
