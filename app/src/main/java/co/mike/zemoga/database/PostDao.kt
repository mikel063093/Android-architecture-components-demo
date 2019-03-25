package co.mike.zemoga.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.mike.zemoga.models.Post
import io.reactivex.Flowable

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getPosts(): Flowable<List<Post>>

    @Query("SELECT * FROM posts WHERE favorite = 1")
    fun getFavoritePost(): Flowable<List<Post>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPost(id: Int): Flowable<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    fun bulkInsert(postEntries: List<Post>)

    @Query("UPDATE posts SET read = 1 WHERE id = :id")
    fun read(id: Int)

    @Query("UPDATE posts SET favorite = :value WHERE id = :id")
    fun favorite(id: Int, value: Int)

    @Query("DELETE FROM posts")
    fun deleteAll()
}