package co.mike.zemoga.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.mike.zemoga.R
import co.mike.zemoga.actions.PostActions
import co.mike.zemoga.activities.DetailActivity
import co.mike.zemoga.activities.POST_ID
import co.mike.zemoga.base.BaseFragment
import co.mike.zemoga.base.adapter.PostAdapter
import co.mike.zemoga.databinding.FragmentPostsBinding
import co.mike.zemoga.models.Post
import co.mike.zemoga.viewmodels.PostsViewModel
import co.mike.zemoga.viewmodels.SharedViewModel
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

    private lateinit var sharedViewModel: SharedViewModel

    private var binding: FragmentPostsBinding? = null

    private var adapter: PostAdapter? = null

    private var isFavorite: Boolean? = false

    private val observerClearAdapter = Observer<Boolean> { clearItems(it!!) }

    private val observerReload = Observer<Boolean> { sync(it!!) }

    private fun clearItems(clear: Boolean) {
        if (clear) {
            adapter?.loadPosts(arrayListOf())
        }
    }

    private fun sync(load: Boolean) {
        if (load) {
            viewModel.reload()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isFavorite = arguments?.getBoolean(KEY_FAVORITE)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)
        }
        adapter = PostAdapter(requireContext(), ArrayList(), this)
        setupRecyclerView()

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val owner = viewLifecycleOwner ?: this
        lifecycle.addObserver(viewModel)
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.clearItems().observe(owner, observerClearAdapter)
        sharedViewModel.reload().observe(owner, observerReload)
        bindToViewModel()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun initViewModel() {
        isFavorite?.let { favorite ->
            if (favorite) {
                viewModel.loadFavoritePosts()
            } else {
                viewModel.loadPosts()
            }
        }
    }

    private fun bindToViewModel() {
        compositeDisposable.add(viewModel.getActions().subscribe(this::handleActions))
    }

    private fun handleActions(event: PostActions) {
        when (event) {
            is PostActions.ShowPosts -> showPosts(event.posts)
            is PostActions.ShowFavortePosts -> showFavortePosts(event.posts)
            is PostActions.ShowLoading -> showLoading(event.loading)
            is PostActions.ShowError -> showError(event.error)
        }
    }

    private fun showPosts(posts: List<Post>) {
        isFavorite?.let {
            if (it.not()) {
                adapter?.loadPosts(posts)
            }
        }
    }

    private fun showFavortePosts(posts: List<Post>) {
        isFavorite?.let {
            if (it) {
                adapter?.loadPosts(posts)
            }
        }
    }

    override fun onItemClicked(itemView: Post) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(POST_ID, itemView.id)
        requireActivity().startActivity(intent)
    }

    private fun setupRecyclerView() {
        val recycler = binding?.recyclerView
        recycler?.adapter = adapter
        recycler?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
    }
}