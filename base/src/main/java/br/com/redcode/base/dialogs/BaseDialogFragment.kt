package br.com.redcode.base.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

/**
 * Created by pedrofsn on 19/10/2017.
 */
abstract class BaseDialogFragment : DialogFragment() {

    abstract var layout: Int
    abstract var canceledOnTouchOutside: Boolean

    open var dialogWidth = 300
    open var dialogHeight = 250

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        dialog?.setCanceledOnTouchOutside(canceledOnTouchOutside)
        configureDialogSize()
        initView(view)
        afterOnCreate()
        return view
    }

    private fun configureDialogSize() {
        val window = dialog?.window

        if (window != null) {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.requestFeature(Window.FEATURE_NO_TITLE)
            window.setLayout(dialogWidth, dialogHeight)
        }
    }

    abstract fun initView(view: View?)

    abstract fun afterOnCreate()

}