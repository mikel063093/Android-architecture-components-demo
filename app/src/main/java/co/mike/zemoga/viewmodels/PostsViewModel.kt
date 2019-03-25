package co.mike.zemoga.viewmodels

import co.mike.zemoga.actions.PostActions
import co.mike.zemoga.base.ViewModel
import co.mike.zemoga.database.PostDao
import co.mike.zemoga.extencion.applySchedulers
import co.mike.zemoga.models.Post
import co.mike.zemoga.services.ZemogaService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PostsViewModel @Inject constructor(private val service: ZemogaService, private val postDao: PostDao) : ViewModel() {

    private val actions by lazy { PublishSubject.create<PostActions>() }

    fun loadPosts() {
        disposable.add(service.fetchPosts()
                .applySchedulers()
                .doOnSubscribe { showLoading(true) }
                .doOnSuccess { showLoading(false) }
                .doOnError { showLoading(false) }
                .subscribe(this::hadlePosts, this::onError)
        )
    }

    private fun hadlePosts(posts: List<Post>) {
        disposable.add(savePosts(posts).applySchedulers().subscribe())
        actions.onNext(PostActions.ShowPosts(posts))
    }

    fun loadFavortePosts() {
        disposable.add(postDao.getFavoritePost()
                .applySchedulers()
                .doOnSubscribe { showLoading(true) }
                .doOnError { showLoading(false) }
                .subscribe(this::hadleFavoritePosts, this::onError)
        )
    }

   private fun savePosts(posts: List<Post>): Completable {
        return Completable.fromCallable { postDao.bulkInsert(posts) }
    }

    private fun hadleFavoritePosts(posts: List<Post>) {
        actions.onNext(PostActions.ShowPosts(posts))
    }

    private fun onError(error: Throwable?) {
        actions.onNext(PostActions.ShowError(error?.message
                ?: ""))
    }

    private fun showLoading(loading: Boolean) {
        actions.onNext(PostActions.ShowLoading(loading))
    }

    fun onClickDelete() {

    }

    fun getActions(): Observable<PostActions> = actions.hide()
}