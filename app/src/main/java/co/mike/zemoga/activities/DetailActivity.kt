package co.mike.zemoga.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import co.mike.zemoga.R
import co.mike.zemoga.actions.PostActions
import co.mike.zemoga.base.BaseActivity
import co.mike.zemoga.base.adapter.CommentsAdapter
import co.mike.zemoga.databinding.ActivityPostDetailBinding
import co.mike.zemoga.extencion.inject
import co.mike.zemoga.models.Comment
import co.mike.zemoga.viewmodels.DetailViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

const val POST_ID = "postId"

class DetailActivity : BaseActivity() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val binding: ActivityPostDetailBinding by lazy {
        DataBindingUtil.setContentView<ActivityPostDetailBinding>(this, R.layout.activity_post_detail)
    }

    @Inject
    lateinit var viewModel: DetailViewModel

    private var postId: String? = null

    private var adapter: CommentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        lifecycle.addObserver(viewModel)
        this.postId = intent?.extras?.getString(POST_ID)
        adapter = CommentsAdapter(baseContext, ArrayList())
        bindToViewModel()
        setupRecyclerView()
    }

    private fun bindToViewModel() {
        compositeDisposable.add(viewModel.getActions().subscribe(this::handleActions))
    }

    private fun handleActions(event: PostActions) {
        when (event) {
            is PostActions.ShowLoading -> showLoading(event.loading)
            is PostActions.ShowError -> showError(event.error)
            is PostActions.ShowComments -> showComments(event.comments)
        }
    }

    private fun showComments(comments: List<Comment>) {
        adapter?.loadItems(comments)
    }

    override fun onResume() {
        super.onResume()
        this.postId?.let {
            viewModel.loadDetail(it.toInt())
        }
    }

    private fun setupRecyclerView() {
        val recycler = binding.commentsRecyclerView
        recycler.adapter = adapter
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(baseContext)
    }
}