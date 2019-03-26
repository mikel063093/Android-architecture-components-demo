package co.mike.zemoga.viewmodels

import co.mike.zemoga.actions.PostActions
import co.mike.zemoga.base.ViewModel
import co.mike.zemoga.database.PostDao
import co.mike.zemoga.database.UserDao
import co.mike.zemoga.extencion.applySchedulers
import co.mike.zemoga.models.Post
import co.mike.zemoga.models.User
import co.mike.zemoga.services.ZemogaService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PostsViewModel @Inject constructor(private val service: ZemogaService,
                                         private val postDao: PostDao,
                                         private val userDao: UserDao) : ViewModel() {

    private val actions by lazy { PublishSubject.create<PostActions>() }

    private lateinit var sharedViewModel: SharedViewModel

    private fun initViewModel() {
        loadPosts()
        loadUsers()
    }

    fun loadPosts() {
        disposable.add(service.getPosts()
                .applySchedulers()
                .doOnSubscribe { showLoading(true) }
                .doOnSuccess { showLoading(false) }
                .doOnError { showLoading(false) }
                .subscribe(this::handlePosts, this::onError)
        )
    }

    private fun loadUsers() {
        disposable.add(service.getUsers()
                .applySchedulers()
                .doOnSubscribe { showLoading(true) }
                .doOnSuccess { showLoading(false) }
                .doOnError { showLoading(false) }
                .subscribe(this::handleUsers, this::onError))
    }

    private fun handlePosts(posts: List<Post>) {
        disposable.add(savePosts(posts)
                .applySchedulers()
                .subscribe {
                    loadPostFromDataBase()
                })
    }

    private fun loadPostFromDataBase() {
        disposable.add(postDao.getPosts()
                .applySchedulers()
                .subscribe { posts ->
                    if (posts.isEmpty()) {
                        actions.onNext(PostActions.ClearView)
                    } else {
                        actions.onNext(PostActions.ShowPosts(posts))
                    }
                })
    }

    private fun handleUsers(userList: List<User>) {
        disposable.add(saveUsers(userList).applySchedulers().subscribe())
    }

    fun loadFavoritePosts() {
        disposable.add(postDao.getFavoritePost()
                .applySchedulers()
                .doOnSubscribe { showLoading(true) }
                .doOnError { showLoading(false) }
                .subscribe(this::handleFavoritePosts, this::onError)
        )
    }

    private fun savePosts(posts: List<Post>): Completable {
        return Completable.fromCallable { postDao.bulkInsert(posts) }
    }

    private fun deleteAllPosts(): Completable {
        return Completable.fromCallable { postDao.deleteAll() }
    }

    private fun saveUsers(userList: List<User>): Completable {
        return Completable.fromCallable { userDao.insert(userList) }
    }


    private fun handleFavoritePosts(posts: List<Post>) {
        actions.onNext(PostActions.ShowFavoritePosts(posts))
    }

    private fun onError(error: Throwable?) {
        actions.onNext(PostActions.ShowError(error?.message
                ?: ""))
    }

    private fun showLoading(loading: Boolean) {
        actions.onNext(PostActions.ShowLoading(loading))
    }

    fun onClickDelete() {
        disposable.add(deleteAllPosts().applySchedulers().subscribe())
        actions.onNext(PostActions.ClickDeleteAll)
    }

    fun reload() {
        initViewModel()
    }

    fun onCLickSync() {
        disposable.add(deleteAllPosts().applySchedulers().subscribe())
        actions.onNext(PostActions.ClickDeleteAll)
        actions.onNext(PostActions.ClickSync)
    }

    fun getActions(): Observable<PostActions> = actions.hide()
}