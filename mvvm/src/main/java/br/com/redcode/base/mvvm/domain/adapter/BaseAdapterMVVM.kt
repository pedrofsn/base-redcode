package br.com.redcode.base.mvvm.domain.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import br.com.redcode.base.mvvm.extensions.isValid
import br.com.redcode.easyrecyclerview.library.extension_functions.clearAndAddAll
import br.com.redcode.spinnable.library.domain.Selectable
import br.com.redcode.spinnable.library.extensions_functions.select
import br.com.redcode.spinnable.library.model.Spinnable

/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class BaseAdapterMVVM<Data, B : ViewDataBinding>(var myOnItemClickListener: ((Data, Int) -> Unit)? = null) : RecyclerView.Adapter<BaseViewHolderMVVM<Data, B>>() {

    abstract var click: ((Data, Int) -> Unit)?
    private val original = arrayListOf<Data>()
    val items = ArrayList<Data>()
    abstract val layout: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderMVVM<Data, B> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: B = DataBindingUtil.inflate(inflater, layout, parent, false)
        return getViewHolder(binding)
    }

    abstract fun getViewHolder(binding: B): BaseViewHolderMVVM<Data, B>

    override fun onBindViewHolder(holder: BaseViewHolderMVVM<Data, B>, position: Int) = holder.bind(
            data = items[position],
            onClick = click
    )

    override fun getItemCount() = items.size

    open fun setCustomList(customList: List<Data>?) {
        if (customList != null) {
            this.items.clear()
            this.items.addAll(customList)
            notifyDataSetChanged()
        }

        getFilterable()?.let {
            if (original.isEmpty() && customList?.isNotEmpty() == true) {
                original.clearAndAddAll(customList)
            }
        }
    }

    fun addAll(novaLista: List<Data>?) {
        if (novaLista != null) {
            if (itemCount == 0) {
                setCustomList(novaLista)
            } else {
                val tamanhoAtual = this.items.size
                val tamanhoNovo = novaLista.size
                val total = tamanhoAtual + tamanhoNovo

                this.items.addAll(novaLista)
                notifyItemRangeInserted(tamanhoAtual, total)
            }
        }
    }

    fun add(item: Data?) {
        if (item != null) {
            val tamanhoAtual = this.items.size
            val total = tamanhoAtual + 1

            this.items.add(item)
            notifyItemRangeInserted(tamanhoAtual, total)
        }
    }

    fun remove(index: Int) {
        this.items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun isEmpty() = items.size == 0

    open fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun getItem(index: Int) = items[index]

    fun select(position: Int) {
        if ((items as? List<Spinnable>)?.select(position) == true) {
            notifyDataSetChanged()
        }
    }

    fun selectOnly(position: Int) {
        val indexSelected = (items as? List<Spinnable>)?.indexOfFirst { it.selected }

        if (indexSelected?.isValid() == true) {
            (getItem(indexSelected) as Spinnable).selected = false
            notifyItemChanged(indexSelected)
        }

        (getItem(position) as Spinnable).selected = true
        notifyItemChanged(position)
    }

    fun getSelected() = items.firstOrNull { obj -> (obj as? Selectable)?.selected == true }

    open fun filter(query: String?) {
        getFilterable()?.let {
            if (query?.isNotBlank() == true) {
                setCustomList(items.filter { obj ->
                    getFilterable()?.invoke(
                            obj,
                            query
                    ) == true
                }) // obj.getTextLine1().contains(query) || obj.getTextLine2().contains(query)
            } else {
                setCustomList(original)
            }
        }
    }

    open fun getFilterable(): ((Data, query: String) -> Boolean)? = null
}