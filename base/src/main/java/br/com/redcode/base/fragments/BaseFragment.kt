package br.com.redcode.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.redcode.base.R
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.extensions.goTo
import br.com.redcode.base.extensions.gone
import br.com.redcode.base.extensions.visible
import br.com.redcode.base.interfaces.Alertable
import br.com.redcode.base.interfaces.Progressable
import br.com.redcode.base.utils.Constants.EMPTY_STRING

/**
 * Created by pedrofsn on 17/10/2017.
 */
abstract class BaseFragment : Fragment(), Alertable, Progressable {

    abstract val layout: Int
    var linearLayoutProgressBar: LinearLayout? = null

    override var processing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
    }

    open fun setupUI() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        middleOnCreate()
        afterOnCreate()
    }

    open fun middleOnCreate() {

    }

    open fun initView(view: View?) {
        linearLayoutProgressBar = view?.findViewById(R.id.linearLayoutProgressBar)
    }

    open fun afterOnCreate() {}

    override fun showProgress() {
        if (activity != null) {
            activity?.runOnUiThread {
                linearLayoutProgressBar?.visible()
                getContentView()?.gone()
            }
        }
        (activity as BaseActivity).processing = true
    }

    override fun hideProgress() {
        if (activity != null) {
            activity?.runOnUiThread {
                linearLayoutProgressBar?.gone()
                getContentView()?.visible()
            }
        }
        (activity as BaseActivity).processing = false
    }

    fun showOrHideProgress(show: Boolean) = if (show) showProgress() else hideProgress()

    open fun getContentView(): View? = null

    override fun showSimpleAlert(message: Any?, function: (() -> Unit)?) {
        (activity as? BaseActivity)?.showSimpleAlert(message, function)
    }

    override fun showMessage(message: Any?, duration: Int) {
        (activity as? BaseActivity)?.showMessage(message, duration)
    }

    override fun toast(message: Any?, duration: Int?) {
        (activity as? BaseActivity)?.toast(message, duration)
    }

    inline fun <reified Activity : AppCompatActivity> goTo(
        params: Pair<String, Any?>? = null,
        requestCode: Int? = null
    ) {
        val activity = context as? BaseActivity

        activity?.goTo<Activity>(params, requestCode)
    }

    override fun finish() = (activity as BaseActivity).finish()
}

fun Context.getSafeString(message: Any?): String = when {
    message != null && message is String -> message
    message != null && message is Int -> getString(message)
    else -> EMPTY_STRING
}