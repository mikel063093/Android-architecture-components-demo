package co.mike.zemoga.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import co.mike.zemoga.R
import co.mike.zemoga.actions.PostActions
import co.mike.zemoga.base.BaseFragment
import co.mike.zemoga.base.adapter.PostAdapter
import co.mike.zemoga.databinding.FragmentPostsBinding
import co.mike.zemoga.models.Post
import co.mike.zemoga.viewmodels.PostsViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


const val KEY_FAVORITE = "favorite"

class FragmentPostList : BaseFragment(), PostAdapter.OnItemClickListener {

    companion object {
        fun newInstance(favorite: Boolean): FragmentPostList {
            val fragment = FragmentPostList()
            val arguments = Bundle()
            arguments.putBoolean(KEY_FAVORITE, favorite)
            fragment.arguments = arguments
            return fragment
        }
    }

    private val compositeDisposable by lazy { CompositeDisposable() }
    @Inject
    lateinit var viewModel: PostsViewModel

    private var binding: FragmentPostsBinding? = null

    private var adapter : PostAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val isFavorite = arguments?.getBoolean(KEY_FAVORITE)

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)
        }
        adapter = PostAdapter(requireContext(), ArrayList(), this)
        setupRecyclerView()
        isFavorite?.let { favorite ->
            if (favorite) {
                viewModel.loadPosts()
            } else {
                viewModel.loadFavortePosts()
            }
        }

        return binding?.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        bindToViewModel()
    }

    private fun bindToViewModel() {
        compositeDisposable.add(viewModel.getActions().subscribe(this::handleActions))
    }

    private fun handleActions(event: PostActions) {
        when (event) {
            is PostActions.ShowPosts -> showPosts(event.posts)
            is PostActions.ShowLoading -> showLoading(event.loading)
            is PostActions.ShowError -> showError(event.error)
        }
    }

    private fun showPosts(posts: List<Post>) {
        adapter?.loadPosts(posts)
    }

    override fun onItemClicked(itemView: Post) {
    }

    private fun setupRecyclerView() {
        val recycler = binding?.recyclerView
        recycler?.adapter = adapter
        recycler?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
    }
}