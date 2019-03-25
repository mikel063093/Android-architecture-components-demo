package co.mike.zemoga.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.mike.zemoga.models.Comment
import io.reactivex.Flowable

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE postId = :postId")
    fun getComments(postId: Int): Flowable<List<Comment>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun bulkInsert(commentEntries: List<Comment>)
}