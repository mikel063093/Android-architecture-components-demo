package co.mike.zemoga.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.mike.zemoga.actions.PostActions
import co.mike.zemoga.base.ViewModel
import co.mike.zemoga.database.CommentDao
import co.mike.zemoga.database.PostDao
import co.mike.zemoga.database.UserDao
import co.mike.zemoga.extencion.applySchedulers
import co.mike.zemoga.models.Comment
import co.mike.zemoga.models.Post
import co.mike.zemoga.models.User
import co.mike.zemoga.services.ZemogaService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val service: ZemogaService,
                                          private val postDao: PostDao,
                                          private val userDao: UserDao,
                                          private val commentDao: CommentDao) : ViewModel() {

    private val actions by lazy { PublishSubject.create<PostActions>() }

    private var postId: Int? = null

    val title = ObservableField("")
    val content = ObservableField("")
    val userName = ObservableField("")
    val userPhone = ObservableField("")
    val userEmail = ObservableField("")
    val userWebsite = ObservableField("")
    val isFavorite = ObservableBoolean()

    fun loadDetail(postId: Int) {
        this.postId = postId
        updatePost(postId, isRead = true)
        disposable.add(postDao.getPost(postId)
                .applySchedulers()
                .subscribe(this::handlePost, this::onError)
        )
    }

    private fun handlePost(post: Post) {
        this.isFavorite.set(post.favorite)
        title.set(post.title)
        content.set(post.body)
        isFavorite.set(post.favorite)
        getUser(post.userId)
        getComments(post.id)
    }

    private fun getComments(postId: String) {
        disposable.add(service.getComments(postId)
                .applySchedulers()
                .doOnSubscribe { showLoading(true) }
                .doOnSuccess { showLoading(false) }
                .doOnError { showLoading(false) }
                .subscribe(this::handleComments, this::onError)
        )
    }

    private fun handleComments(comments: List<Comment>) {
        actions.onNext(PostActions.ShowComments(comments))
    }

    private fun getUser(userId: String) {
        disposable.add(userDao.getUser(userId).applySchedulers().subscribe(this::handleUser, this::onError))
    }

    private fun handleUser(user: User) {
        userName.set(user.name)
        userPhone.set(user.phone)
        userWebsite.set(user.website)
        userEmail.set(user.username)
    }

    private fun onError(error: Throwable?) {
        actions.onNext(PostActions.ShowError(error?.message
                ?: ""))
    }

    private fun showLoading(loading: Boolean) {
        actions.onNext(PostActions.ShowLoading(loading))
    }

    fun onClickFavorite() {
        postId?.let {
            this.isFavorite.set(this.isFavorite.get().not())
            updatePost(it, isFavorite = this.isFavorite.get())
        }
    }

    private fun updatePost(postId: Int, isRead: Boolean = false, isFavorite: Boolean = false) {
        disposable.add(putPost(postId, isRead, isFavorite).applySchedulers().subscribe())
    }

    private fun putPost(postId: Int, isRead: Boolean = false, isFavorite: Boolean = false): Completable {
        return Completable.fromCallable {
            if (isRead) {
                postDao.read(postId)
            }
            postDao.favorite(postId, if (isFavorite) 1 else 0)
        }
    }

    fun getActions(): Observable<PostActions> = actions.hide()
}