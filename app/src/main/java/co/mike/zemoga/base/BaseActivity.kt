package co.mike.zemoga.base

import android.animation.AnimatorSet
import android.os.Build
import android.transition.Slide
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.transition.addListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import co.mike.zemoga.R
import co.mike.zemoga.extencion.removeFromParent
import co.mike.zemoga.view.LoadingView

open class BaseActivity : AppCompatActivity(), AndroidProvider {

    private val loadingView by lazy { LoadingView(this) }


    fun setupAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = buildEnterAnimation()
            window.returnTransition = buildReturnAnimation()
        } else {
            overridePendingTransition(R.anim.in_from_bottom, 0)
            showViews(AnimatorSet())
        }
    }

    open fun showViews(animator: AnimatorSet) {}


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun buildEnterAnimation(): Slide {
        val transition = Slide()
        transition.slideEdge = Gravity.BOTTOM
        transition.duration = 500
        transition.interpolator = FastOutSlowInInterpolator()
        transition.excludeTarget(android.R.id.navigationBarBackground, true)
        transition.excludeTarget(android.R.id.statusBarBackground, true)
        transition.addListener(onStart = {
            val animator = AnimatorSet()
            animator.startDelay = 200
            showViews(animator)
        })
        return transition
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun buildReturnAnimation(): Slide {
        val transition = Slide()
        transition.slideEdge = Gravity.BOTTOM
        transition.duration = 400
        transition.interpolator = FastOutSlowInInterpolator()
        transition.excludeTarget(android.R.id.navigationBarBackground, true)
        transition.excludeTarget(android.R.id.statusBarBackground, true)
        return transition
    }

    override fun showProgressDialog(message: String) {
        loadingView.setMessage(message)
        if (loadingView.parent == null) {
            addContentView(loadingView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) showProgressDialog() else dismissProgressDialog()
    }

    override fun showProgressDialog(@StringRes idMessage: Int) {
        showProgressDialog(getString(idMessage))
    }

    override fun dismissProgressDialog() {
        loadingView.removeFromParent()
    }

    override fun showMessage(message: String) {
        dismissProgressDialog()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: String) {
        dismissProgressDialog()

    }

    override fun showError(stringId: Int) {
        dismissProgressDialog()
    }

    override fun showMessage(@StringRes messageRes: Int) {
        showMessage(getString(messageRes))
    }

    override fun showProgressDialog() {
        showProgressDialog(R.string.copy_loading)
    }
}