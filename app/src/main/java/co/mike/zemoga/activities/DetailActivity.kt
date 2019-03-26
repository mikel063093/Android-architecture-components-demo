package co.mike.zemoga.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import co.mike.zemoga.R
import co.mike.zemoga.base.BaseActivity
import co.mike.zemoga.databinding.ActivityPostDetailBinding
import co.mike.zemoga.extencion.inject
import co.mike.zemoga.viewmodels.DetailViewModel
import javax.inject.Inject

const val POST_ID = "postId"

class DetailActivity : BaseActivity() {

    private val binding: ActivityPostDetailBinding by lazy {
        DataBindingUtil.setContentView<ActivityPostDetailBinding>(this, R.layout.activity_post_detail)
    }

    @Inject
    lateinit var viewModel: DetailViewModel

    private var postId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        lifecycle.addObserver(viewModel)
        this.postId = intent?.extras?.getString(POST_ID)

    }

    override fun onResume() {
        super.onResume()
        this.postId?.let {
            viewModel.loadDetail(it.toInt())
        }
    }
}