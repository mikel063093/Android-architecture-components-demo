package co.mike.zemoga.services

import co.mike.zemoga.models.Comment
import co.mike.zemoga.models.Post
import co.mike.zemoga.models.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val POSTS = "posts"
const val USERS = "users"
const val COMMENTS = "comments"

interface ZemogaService {
    @GET(POSTS)
    fun getPosts(): Single<List<Post>>

    @GET(USERS)
    fun getUsers(): Single<List<User>>

    @GET(COMMENTS)
    fun getComments(@Query("postId") postId: String): Single<List<Comment>>
}