package br.com.redcode.base.mvvm.databinding.bindings

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import br.com.redcode.base.extensions.*

object BindingAdapters {

// -------------------------------------------------------------------------------------------------> View

    @JvmStatic
    @BindingAdapter("app:showOrHide")
    fun showOrHide(view: View?, case: Any?) {
        when (case) {
            null -> view?.showOrHide(false)
            is Boolean -> view?.showOrHide(case)
            is String -> view?.showOrHide(case.isNotBlank())
            else -> view?.showOrHide(true)
        }
    }

    @JvmStatic
    @BindingAdapter("app:beInvisible")
    fun beInvisible(view: View?, visible: Boolean?) {
        if (visible != null && view != null) {
            if (visible) view.invisible() else view.visible()
        }
    }

    @JvmStatic
    @BindingAdapter("app:shouldGone")
    fun shouldGone(view: View?, yesToGone: Boolean?) {
        if (yesToGone != null && view != null) {
            if (yesToGone) view.gone() else view.visible()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:changeColorTo", "app:case"], requireAll = true)
    fun changeColorTo(view: View?, @ColorInt color: Int?, case: Boolean) {
        if (case) changeBackground(
            view,
            color
        )
    }

    @JvmStatic
    @BindingAdapter("app:background")
    fun changeBackground(view: View?, @ColorInt color: Int?) {
        if (color != null && view != null) {
            try {
                view.background = ColorDrawable(color)
            } catch (e: Exception) {
                throw RuntimeException("Hexadecimal inválido '$color' para o '${view.background}'!")
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:backgroundFromHex")
    fun backgroundFromHex(view: View?, color: String?) {
        if (color != null && view != null && color.isNotBlank()) {
            try {
                view.setBackgroundColor(color.parseColor())
            } catch (e: Exception) {
                throw RuntimeException("Hexadecimal inválido '$color'!")
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:tintBackgroundFromHex")
    fun tintBackgroundFromHex(view: View?, color: String?) {
        if (color != null && view != null && color.isNotBlank()) {
            try {
                view.background?.changeDrawableColor(color)
            } catch (e: Exception) {
                throw RuntimeException("Hexadecimal inválido '$color'!")
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:tintBackgroundFromHex")
    fun tintBackgroundFromHex(view: View?, @ColorInt color: Int?) {
        if (color != null && view != null) {
            try {
                view.background?.changeDrawableColor(color)
            } catch (e: Exception) {
                throw RuntimeException("Hexadecimal inválido '$color' para o '${view.background}'!")
            }
        }
    }

// -------------------------------------------------------------------------------------------------> TextView

    @JvmStatic
    @BindingAdapter("app:textColor")
    fun textColor(view: TextView?, color: String?) {
        if (color != null && view != null) {
            try {
                if (color.isNotBlank()) {
                    view.setTextColor(color.parseColor())
                }
            } catch (e: Exception) {
                throw RuntimeException("Hexadecimal inválido '$color' para o '${view.background}'!")
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:textColor")
    fun textColor(view: TextView?, @ColorInt color: Int?) {
        if (view != null && color != null) {
            view.setTextColor(color)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:changeColorTo", "app:case"], requireAll = true)
    fun changeColorTo(view: TextView?, @ColorInt color: Int?, case: Boolean) = takeIf { case }
        ?.run { textColor(view = view, color = color) }

    @JvmStatic
    @BindingAdapter("app:textSize")
    fun textSize(view: TextView?, @DimenRes dimen: Int?) {
        if (view != null && dimen != null) {
            view.context.resources.apply {
                val textSizeInSp = getDimension(dimen)
                val size =
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP,
                        textSizeInSp,
                        displayMetrics
                    )
                view.textSize = size
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:textOrHide")
    fun textOrHide(view: TextView?, text: String?) {
        if (view != null) {
            if (text.isNullOrBlank().not()) {
                view.text = text
                view.visible()
            } else {
                view.gone()
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:changeTextFrom", "app:changeTextTo", "app:caseText"],
        requireAll = true
    )
    fun changeTextTo(
        textView: TextView?,
        @StringRes changeFrom: Int?,
        @StringRes changeTo: Int?,
        case: Boolean?
    ) {
        if (case != null && changeTo != null && changeFrom != null) {
            textView?.setText(if (case) changeTo else changeFrom)
        }
    }

// -------------------------------------------------------------------------------------------------> RadioButton

    @JvmStatic
    @BindingAdapter(value = ["checkInverse", "checkInverseAttrChanged"], requireAll = false)
    fun bindSpinnerData(
        radioButton: RadioButton,
        newSelectedValue: Boolean?,
        listener: InverseBindingListener?
    ) {

        radioButton.setOnCheckedChangeListener { _, _ -> listener?.onChange() }

        if (newSelectedValue != null) {
            radioButton.isChecked = newSelectedValue.not()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "checkInverse", event = "checkInverseAttrChanged")
    fun captureSelectedValue(spinner: RadioButton): Boolean = spinner.isChecked.not()

// -------------------------------------------------------------------------------------------------> SwitchCompat

    @JvmStatic
    @BindingAdapter(value = ["bind", "bindAttrChanged"], requireAll = false)
    fun bindSwitchCompat(
        switchCompat: SwitchCompat,
        newSelectedValue: Boolean?,
        listener: InverseBindingListener?
    ) {

        switchCompat.setOnCheckedChangeListener { _, _ -> listener?.onChange() }

        if (newSelectedValue != null) {
            switchCompat.isChecked = newSelectedValue
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "bind", event = "bindAttrChanged")
    fun bindSwitchCompat(switchCompat: SwitchCompat): Boolean = switchCompat.isChecked

// -------------------------------------------------------------------------------------------------> EditText

    @JvmStatic
    @BindingAdapter("app:onKeyDone")
    fun onKeyDone(
        editText: EditText?,
        action: OnKeyPressedListener
    ) {
        editText?.setOnKeyListener { _, keyCode, event ->
            var handled = false
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                action.onKeyPressed()

                editText.apply {
                    val inputMethodManager =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
                }

                handled = true
            }
            return@setOnKeyListener handled
        }
    }

}