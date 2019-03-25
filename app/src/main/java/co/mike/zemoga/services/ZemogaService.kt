package co.mike.zemoga.services

import co.mike.zemoga.models.Post
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface ZemogaService {
    @GET("posts")
    fun fetchPosts(): Single<List<Post>>
}