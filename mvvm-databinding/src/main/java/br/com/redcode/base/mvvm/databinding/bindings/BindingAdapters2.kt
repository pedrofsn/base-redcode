package br.com.redcode.base.mvvm.databinding.bindings

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import br.com.redcode.base.mvvm.databinding.R
import br.com.redcode.base.mvvm.extensions.isValid
import br.com.redcode.easyrecyclerview.library.extension_functions.handleNestedScrolling
import br.com.redcode.spinnable.library.adapter.AdapterSpinneable
import br.com.redcode.spinnable.library.extensions_functions.*
import br.com.redcode.spinnable.library.model.Spinnable
import com.google.android.material.textfield.TextInputLayout


// -------------------------------------------------------------------------------------------------> Button

@BindingAdapter("app:changeBackground")
fun changeDrawable(view: Button, drawable: Drawable) {
    view.background = drawable
}

@SuppressLint("ResourceAsColor")
@BindingAdapter("app:tinty")
fun pimpMyRide(view: Button, @ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        try {
            view.background?.setTint(color)
        } catch (e: Exception) {
            throw RuntimeException("Hexadecimal inválido '$color' para o '${view.background}'!")
        }
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("android:text")
fun setText(view: Button, @StringRes value: Int?) {
    try {
        if (value != null && value > 0) {
            view.text = view.context.getString(value)
        }
    } catch (e: Exception) {
        // ignore
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("app:textFromResource")
fun setTextFromResource(view: Button, @StringRes value: Int?) {
    try {
        if (value != null && value > 0) {
            view.text = view.context.getString(value)
        }
    } catch (e: Exception) {
        // ignore
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("app:textFromResource")
fun setTextFromResource(view: Button, value: ObservableField<Int>?) {
    try {
//        value?.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//
//            }
//        })
        value?.get()?.let { idString ->
            if (idString.isValid()) {
                view.text = view.context.getString(idString)
            }
        }
    } catch (e: Exception) {
        // ignore
    }
}

// -------------------------------------------------------------------------------------------------> RecyclerView

@BindingAdapter("app:handleNestedScrolling")
fun handleNestedScrolling(recyclerView: RecyclerView?, handle: Boolean?) {
    if (handle == true) {
        recyclerView?.handleNestedScrolling()
    }
}

// -------------------------------------------------------------------------------------------------> ImageView

@BindingAdapter(value = ["app:changeFrom", "app:changeTo", "app:case"], requireAll = true)
fun changeImageTo(
    imageView: ImageView?, @ColorInt changeFrom: Int?, @ColorInt changeTo: Int?,
    case: Boolean
) =
    changeBackground(imageView, if (case) changeTo else changeFrom)


@SuppressLint("ResourceAsColor")
@BindingAdapter(value = ["app:tint", "app:if"], requireAll = true)
fun pimpMyRide(imageView: ImageView, @ColorRes tint: Int, case: Boolean) {
    if (case) {
        val colorStateList = ColorStateList.valueOf(tint)
        ImageViewCompat.setImageTintList(imageView, colorStateList)
    } else {
        imageView.refreshDrawableState()
    }
}

// -------------------------------------------------------------------------------------------------> AutoCompleteTextView

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter(
    value = ["app:selectedValueAttrChanged", "app:entries", "app:trigger"],
    requireAll = false
)
fun setListener(
    view: AutoCompleteTextView,
    listener: InverseBindingListener?,
    entries: List<Spinnable>?,
    trigger: (() -> Unit)?
) {
    val stringEntries = entries?.map { it.description } ?: emptyList()

    val adapter = ArrayAdapter(
        view.context,
        R.layout.support_simple_spinner_dropdown_item,
        stringEntries
    )

    view.setAdapter(adapter)

    val watcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val input = view.editableText?.toString()
            val count = input?.count() ?: 0

            if (count > 0) {
                val index = stringEntries.indexOf(input)
                entries?.select(index)

                listener?.onChange()
                trigger?.invoke()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    view.addTextChangedListener(watcher)
    view.inputType = InputType.TYPE_NULL
    view.setOnTouchListener { _, _ ->
        view.showDropDown()
        return@setOnTouchListener true
    }
}

@InverseBindingAdapter(attribute = "app:selectedValue", event = "app:selectedValueAttrChanged")
fun getSelectedText(autoCompleteTextView: AutoCompleteTextView) =
    autoCompleteTextView.editableText.toString()

// -------------------------------------------------------------------------------------------------> Spinner

@SuppressLint("ResourceType")
@BindingAdapter(
    value = ["app:selectedValue", "app:selectedValueAttrChanged", "app:entries", "app:defaultString", "app:defaultStringAsInt", "app:trigger"],
    requireAll = false
)
fun bindSpinnerData(
    spinner: AppCompatSpinner,
    newSelectedValue: Any?,
    newTextAttrChanged: InverseBindingListener?,
    entries: List<Spinnable>?,
    defaultString: String?,
    @StringRes defaultStringAsInt: Int?,
    trigger: (() -> Unit)?
) {
    if (entries != null) {
        val oldAdapter = spinner.adapter
        var changed = true
        if (oldAdapter != null && oldAdapter.count == entries.size) {
            changed = false
            for (i in entries.indices) {
                if (entries[i] != oldAdapter.getItem(i)) {
                    changed = true
                    break
                }
            }
        }

        if (changed) {
            val hasDefaultString = defaultString?.isNotBlank() == true
            val hasDefaultInt = defaultStringAsInt?.isValid() == true && defaultStringAsInt > 0

            val mDefault = when {
                hasDefaultString -> defaultString
                hasDefaultInt && defaultStringAsInt != null -> spinner.context.getString(
                    defaultStringAsInt
                )
                else -> null
            }

            spinner.setSpinnable(
                entries,
                mDefault,
                entries.getSelected()?.id
            ) { _, _ ->
                newTextAttrChanged?.onChange()
                trigger?.invoke()
            }

            spinner.setSelection(entries.getSelectedPosition(), false, hasDefaultString)
        }
    } else {
        spinner.adapter = null
    }
}

@InverseBindingAdapter(attribute = "app:selectedValue", event = "app:selectedValueAttrChanged")
fun captureSelectedValue(spinner: Spinner): Any {
    (spinner.adapter as AdapterSpinneable).select(spinner.selectedItemPosition)
    return (spinner.adapter as AdapterSpinneable).getSpinnables()[spinner.selectedItemPosition]
}

// -------------------------------------------------------------------------------------------------> Checkbox

@BindingAdapter(value = ["check", "checkAttrChanged"], requireAll = false)
fun checkCheckbox(
    view: CheckBox,
    newSelectedValue: Boolean?,
    listener: InverseBindingListener?
) {

    view.setOnCheckedChangeListener { _, _ -> listener?.onChange() }

    if (newSelectedValue != null) {
        view.isChecked = newSelectedValue
    }
}

@InverseBindingAdapter(attribute = "check", event = "checkAttrChanged")
fun checkCheckbox(view: CheckBox): Boolean = view.isChecked

// -------------------------------------------------------------------------------------------------> SwitchCompat

@BindingAdapter(value = ["check", "checkAttrChanged"], requireAll = false)
fun checkSwitchCompat(
    view: SwitchCompat,
    newSelectedValue: Boolean?,
    listener: InverseBindingListener?
) {

    view.setOnCheckedChangeListener { _, _ -> listener?.onChange() }

    if (newSelectedValue != null) {
        view.isChecked = newSelectedValue
    }
}

@InverseBindingAdapter(attribute = "check", event = "checkAttrChanged")
fun checkSwitchCompat(view: SwitchCompat): Boolean = view.isChecked

// -------------------------------------------------------------------------------------------------> TextView

@BindingAdapter(value = ["app:changeFrom", "app:changeTo", "app:case"], requireAll = true)
fun changeText(textView: TextView?, changeFrom: String?, changeTo: String?, case: Boolean) {
    val result = if (case) changeTo else changeFrom
    textView?.text = result
}

@BindingAdapter(value = ["app:changeColorFrom", "app:changeColorTo", "app:case"], requireAll = true)
fun changeText(textView: TextView?, changeFrom: Int?, changeTo: Int?, case: Boolean) {
    val result = if (case) changeTo else changeFrom
    result?.let { textView?.setTextColor(it) }
}

@SuppressLint("ResourceType")
@BindingAdapter("android:text")
fun setText(view: TextView, @StringRes value: Int?) {
    try {
        if (value != null && value > 0) {
            view.text = view.context.getString(value)
        }
    } catch (e: Exception) {
        // ignore
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("app:textFromResource")
fun setTextFromResource(view: TextView, @StringRes value: Int?) {
    try {
        if (value != null && value > 0) {
            view.text = view.context.getString(value)
        }
    } catch (e: Exception) {
        // ignore
    }
}

@BindingAdapter("layout_height")
fun setLayoutHeight(view: View, height: Float) {
    val layoutParams = view.layoutParams
    layoutParams.height = height.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("layout_width")
fun setLayoutWidth(view: View, height: Float) {
    val layoutParams = view.layoutParams
    layoutParams.width = height.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("size")
fun setLayoutSize(view: View, size: Float) {
    val layoutParams = view.layoutParams
    layoutParams.width = size.toInt()
    layoutParams.height = size.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("app:drawableEnd")
fun setDrawableEnd(view: TextView, @DrawableRes intDrawable: Int?) {
    intDrawable?.let {
        //        left, top, right, and bottom borders
        val drawables = view.compoundDrawables
        val drawable = ContextCompat.getDrawable(view.context, intDrawable)

        if (drawable != null && drawables.size >= 4) {
            val currentLeft = view.compoundDrawables[0]
            val currentTop = view.compoundDrawables[1]
//            val currentRight = view.compoundDrawables[2]
            val currentBottom = view.compoundDrawables[3]

            view.setCompoundDrawables(currentLeft, currentTop, drawable, currentBottom)
        }
    }
}

// -------------------------------------------------------------------------------------------------> TextInputLayout

@BindingAdapter("app:hint")
fun setHint(view: TextInputLayout, idText: Int?) {
    if (idText?.isValid() == true) {
        val text = view.context.getString(idText)
        setHint(view, text)
    }
}

@BindingAdapter("app:hint")
fun setHint(view: TextInputLayout, text: String?) {
    view.hint = text
}