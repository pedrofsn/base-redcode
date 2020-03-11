package br.com.redcode.base.mvvm.databinding.bindings

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener


@BindingAdapter("app:query")
fun setQuery(searchView: SearchView?, query: String?) {
    if (searchView?.query.toString() == query) {
        return
    }

    searchView?.setQuery(query, false)
}

@InverseBindingAdapter(attribute = "app:query", event = "app:queryAttrChanged")
fun getQuery(searchView: SearchView?): String? {
    return searchView?.query?.toString()
}

@BindingAdapter("app:onQueryTextSubmit")
fun setOnQueryTextSubmitListener(
    searchView: SearchView?,
    onQueryTextSubmitListener: OnQueryTextSubmitListener2Way?
) {
    setOnQueryTextListener(
        searchView,
        onQueryTextSubmitListener,
        null
    )
}

@BindingAdapter("app:queryAttrChanged")
fun setQueryAttrChanged(searchView: SearchView?, queryAttrChanged: InverseBindingListener?) {
    setOnQueryTextListener(
        searchView,
        null,
        queryAttrChanged
    )
}

@BindingAdapter("app:onQueryTextSubmit", "app:queryAttrChanged")
fun setOnQueryTextListener(
    searchView: SearchView?,
    onQueryTextSubmitListener: OnQueryTextSubmitListener2Way?,
    queryAttrChanged: InverseBindingListener?
) {
    searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String): Boolean {
            onQueryTextSubmitListener?.onQueryTextSubmit(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            queryAttrChanged?.onChange()
            return false
        }
    })
}