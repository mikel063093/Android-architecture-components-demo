package co.mike.zemoga.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.mike.zemoga.models.Comment
import co.mike.zemoga.models.Post
import co.mike.zemoga.models.User

@Database(entities = [(User::class), (Post::class), (Comment::class)], version = 1, exportSchema = false)
abstract class ZemogaDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao

    companion object {
        private const val DATABASE_NAME = "zemoga"

        @Volatile
        private var INSTANCE: ZemogaDatabase? = null

        fun getInstance(context: Context): ZemogaDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ZemogaDatabase {
            return Room.databaseBuilder(context.applicationContext,
                    ZemogaDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}