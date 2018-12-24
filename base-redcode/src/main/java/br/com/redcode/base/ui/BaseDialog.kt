package br.com.redcode.base.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

abstract class BaseDialog : DialogFragment() {

    abstract var layout: Int
    abstract var canceledOnTouchOutside: Boolean

    open var percentDialogSize: Int = 80
    open var dialogWidth: Int? = null
    open var dialogHeight: Int? = null

    lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layout, container, false)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        configureDialogSize()
        afterOnCreate()
        return mView
    }

    private fun configureDialogSize() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    abstract fun afterOnCreate()

    override fun onResume() {
        super.onResume()
//        val width = activity?.resources?.displayMetrics?.widthPixels
//        val height = activity?.resources?.displayMetrics?.heightPixels
//
//        if (width != null && height != null) {
//            val params = dialog.window?.attributes
//            val hasDimensions = dialogWidth != null && dialogHeight != null
//
//            params?.width = if (hasDimensions) dialogWidth else percentDialogSize * width / 100
//            params?.height = if (hasDimensions) dialogHeight else percentDialogSize * height / 100
//
//            dialog.window?.attributes = params as android.view.WindowManager.LayoutParams
//        }
    }

}