package br.com.redcode.base.activities

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.redcode.base.R

import br.com.redcode.base.extensions.getString
import br.com.redcode.base.extensions.handleEnterKeyboard
import br.com.redcode.base.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by pedrofsn on 02/02/18.
 */
abstract class BaseActivityRecyclerViewSwipeFab(override val layout: Int = R.layout.activity_recyclerview_swipe_and_fab) :
    BaseActivity() {

    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout) }
    private val fab by lazy { findViewById<FloatingActionButton>(R.id.fab) }

    private var searchItem: MenuItem? = null
    private var searchPlate: EditText? = null
    open var query: String? = null

    open fun hasSearchMenuItem() = false
    private fun hasFab() = false
    open fun hasSwipeRefreshLayout() = true

    fun enablePullRefresh() {
        if (hasSwipeRefreshLayout()) {
            swipeRefreshLayout?.setOnRefreshListener { onSwipeRefreshLayout() }

            swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent
            )

            swipeRefreshLayout.isEnabled = true
        } else {
            swipeRefreshLayout.isEnabled = false
        }
    }

    private fun onSwipeRefreshLayout() {
        if (hasSearchMenuItem()) {
            searchPlate?.setText(Constants.EMPTY_STRING)
            hideKeyboard(searchPlate)
            searchItem?.let { (it.actionView as SearchView).isIconified = true }
        }
        loadContent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (hasSearchMenuItem()) {
            menuInflater.inflate(getOptionsMenu(), menu)

            searchItem = menu.findItem(R.id.menuItemSearch)

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

            val searchView = searchItem?.actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchPlate = searchView.findViewById(R.id.search_src_text)
            searchPlate?.hint = getString(R.string.search)
            searchEmptyQuery()
        }

        return true
    }

    private fun searchEmptyQuery() {
        if (hasSearchMenuItem()) {
            searchPlate?.handleEnterKeyboard { search() }
        }
    }

    open fun search(query: String? = searchPlate?.getString()) {
        hideKeyboard(searchPlate)
        this.query = query
    }

    override fun onResume() {
        super.onResume()
        fab.visibility = if (hasFab()) View.VISIBLE else View.GONE
    }

    override fun showProgress() {
        if (hasSwipeRefreshLayout()) {
            processing = true
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = true
            }
        } else {
            super.showProgress()
        }

        if (hasFab() && fab != null) {
            fab.visibility = View.INVISIBLE
        }
    }

    override fun hideProgress() {
        if (hasSwipeRefreshLayout()) {
            processing = false
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = false
            }
        } else {
            super.hideProgress()
        }

        if (hasFab() && fab != null) {
            fab.visibility = View.VISIBLE
        }
    }

    abstract fun loadContent()

    open fun getOptionsMenu() = R.menu.menu_search


    private fun hideKeyboard(editText: EditText?) {
        editText?.apply {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}