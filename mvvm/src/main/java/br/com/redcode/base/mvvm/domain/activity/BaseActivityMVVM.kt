package br.com.redcode.base.mvvm.domain.activity


import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.extensions.putExtras
import br.com.redcode.base.fragments.getSafeString
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.MVVM
import br.com.redcode.base.mvvm.models.EventMessage

abstract class BaseActivityMVVM<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseActivity(),
    MVVM<B, VM> {

    override lateinit var binding: B
    override lateinit var viewModel: VM
    abstract override val classViewModel: Class<VM>
    abstract override val idBRViewModel: Int

    override val observerProcessing by lazy { initObserverProcessing() }
    override val observerEvents by lazy { initObserverEvents() }

    override fun setupLayout() {
        binding = DataBindingUtil.setContentView(this, layout)
        viewModel = ViewModelProviders.of(this).get(classViewModel)
        defineMVVM(this)
        setupUI()

        observeEvents()
        lifecycle.addObserver(viewModel)
    }

    open fun setupUI() {
        observeProcessing()
    }

    override fun handleEvent(eventMessage: EventMessage) {
        runOnUiThread {
            handleEvent(eventMessage.event, eventMessage.obj)
        }
    }


    override fun handleEvent(event: String) {
        runOnUiThread {
            handleEvent(event, null)
        }
    }

    override fun handleEvent(event: String, obj: Any?) {
        runOnUiThread {
            when (event) {
                "showSimpleAlertAndClose" -> getSafeString(obj).let { showSimpleAlert(it) { finish() } }
                else -> super.handleEvent(event, obj)
            }
        }
    }

    fun backToStartFlux() {
        backToStartFlux("backToStartFlux" to true)
    }

    fun backToStartFlux(vararg params: Pair<String, Any?>?) {
        val intent = Intent()
        intent.putExtras(*params)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun showProgress() {
        super<MVVM>.showProgress()
        super<BaseActivity>.showProgress()
    }

    override fun hideProgress() {
        super<MVVM>.hideProgress()
        super<BaseActivity>.hideProgress()
    }
}