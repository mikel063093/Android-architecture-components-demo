package co.mike.zemoga.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import co.mike.zemoga.R
import co.mike.zemoga.actions.PostActions
import co.mike.zemoga.base.BaseActivity
import co.mike.zemoga.base.BaseFragment
import co.mike.zemoga.base.adapter.PagerAdapter
import co.mike.zemoga.databinding.ActivityMainBinding
import co.mike.zemoga.extencion.inject
import co.mike.zemoga.fragments.FragmentPostList
import co.mike.zemoga.viewmodels.PostsViewModel
import co.mike.zemoga.viewmodels.SharedViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val binding: ActivityMainBinding  by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModel: PostsViewModel

    private lateinit var sharedViewModel: SharedViewModel

    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment> = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        binding.viewModel = viewModel
        lifecycle.addObserver(viewModel)
        setupViewPager()
        bindToViewModel()
    }

    private fun bindToViewModel() {
        compositeDisposable.add(viewModel.getActions().subscribe(this::handleActions))
    }

    private fun handleActions(event: PostActions) {
        when (event) {
            is PostActions.ClickDeleteAll -> sharedViewModel.clearItems(true)
            is PostActions.ClickSync -> sharedViewModel.reload(true)
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager, buildFragments())
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)?.text = "ALL"
        binding.tabLayout.getTabAt(1)?.text = "FAVORITE"
    }

    private fun buildFragments(): Array<BaseFragment> {
        val postList = FragmentPostList.newInstance(false)
        val favoriteList = FragmentPostList.newInstance(true)
        return arrayOf(postList, favoriteList)
    }

}
