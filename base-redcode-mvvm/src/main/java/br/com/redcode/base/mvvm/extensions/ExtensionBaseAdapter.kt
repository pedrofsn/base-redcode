package br.com.redcode.base.mvvm.extensions

import br.com.redcode.easyrecyclerview.library.adapter.BaseAdapter
import br.com.redcode.spinnable.library.domain.Selectable

fun BaseAdapter<Selectable, *>.select(position: Int) {
    if (list.isNotEmpty()) {
        list.forEach { it.selected.not() }
        list[position].selected = true
        notifyDataSetChanged()
    }
}

fun BaseAdapter<Selectable, *>.getSelected() = list.firstOrNull { it.selected }