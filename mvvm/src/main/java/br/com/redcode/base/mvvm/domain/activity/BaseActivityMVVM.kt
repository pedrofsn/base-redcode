package br.com.redcode.base.mvvm.domain.activity


import android.app.ProgressDialog
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.mvvm.R
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.extensions.observer
import br.com.redcode.base.mvvm.models.Event
import br.com.redcode.base.mvvm.models.EventMessage

abstract class BaseActivityMVVM<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseActivity() {

    protected lateinit var binding: B
    lateinit var viewModel: VM
    abstract val classViewModel: Class<VM>
    abstract val idBRViewModel: Int

    private val observerProcessing = observer<Boolean> { processing = it }

    private val observerEvents = observer<Event<EventMessage>> {
        it.getContentIfNotHandled()?.let { obj ->
            handleEvent(obj)
        }
    }

    private val progressDialog by lazy { ProgressDialog(this) }

    override fun setupLayout() {
        binding = DataBindingUtil.setContentView(this, layout)
        viewModel = ViewModelProviders.of(this).get(classViewModel)
        binding.setVariable(idBRViewModel, viewModel)
        binding.setLifecycleOwner(this)
        setupUI()

        (viewModel as AbstractBaseViewModel).events.observe(this, observerEvents)
        lifecycle.addObserver(viewModel)
    }

    open fun setupUI() {
        (viewModel as? AbstractBaseViewModel)?.processing?.observe(this, observerProcessing)
    }

    private fun handleEvent(eventMessage: EventMessage) {
        runOnUiThread {
            handleEvent(eventMessage.event, eventMessage.obj)
        }
    }

    fun handleEvent(event: String) {
        runOnUiThread {
            handleEvent(event, null)
        }
    }

    open fun handleEvent(event: String, obj: Any? = null) {
        runOnUiThread {
            val string = if (obj != null && obj is String) obj else null

            when (event) {
                "showSimpleAlert" -> string?.let { showSimpleAlert(it) }
                "showSimpleAlertAndClose" -> string?.let { showSimpleAlert(it) { finish() } }
                "showMessage" -> string?.let { showMessage(it) }
                "showProgressDialog" -> showProgressDialog()
                "hideProgressDialog" -> hideProgressDialog()
                "showProgressbar" -> showProgress()
                "hideProgressbar" -> hideProgress()
                else -> throw RuntimeException("Event '$event' not handled in 'handleEvent' method")
            }
        }
    }

    private fun showProgressDialog() {
        if (progressDialog.isShowing.not()) {
            progressDialog.isIndeterminate = true
            progressDialog.setCancelable(false)
            progressDialog.setMessage(getString(R.string.loading))

            progressDialog.show()
        }
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.hide()
        }
    }

    fun backToStartFlux() {
        val intent = Intent()
        intent.putExtra("backToStartFlux", true)
        setResult(RESULT_OK, intent)
        finish()
    }
}