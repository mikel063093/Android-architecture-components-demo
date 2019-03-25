package co.mike.zemoga.base

open class BaseFragment : ViewLifecycleFragment(), AndroidProvider {

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }


    override fun showError(message: String) {
        getBaseActivity().showError(message)
    }

    override fun showError(messageRes: Int) {
        getBaseActivity().showError(messageRes)
    }

    override fun showMessage(message: String) {
        getBaseActivity().showMessage(message)
    }

    override fun showMessage(messageRes: Int) {
        getBaseActivity().showMessage(messageRes)
    }

    override fun showLoading(show: Boolean) {
        getBaseActivity().showLoading(show)
    }

    override fun showProgressDialog() {
        getBaseActivity().showProgressDialog()
    }

    override fun showProgressDialog(message: String) {
        getBaseActivity().showProgressDialog(message)
    }

    override fun showProgressDialog(messageRes: Int) {
        getBaseActivity().showProgressDialog(messageRes)
    }

    override fun dismissProgressDialog() {
        getBaseActivity().dismissProgressDialog()
    }
}