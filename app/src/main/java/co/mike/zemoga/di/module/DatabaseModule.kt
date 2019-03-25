package co.mike.zemoga.di.module

import android.content.Context
import androidx.room.Room
import co.mike.zemoga.database.CommentDao
import co.mike.zemoga.database.PostDao
import co.mike.zemoga.database.UserDao
import co.mike.zemoga.database.ZemogaDatabase
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    private const val NAME = "zemoga"

    @Provides
    @JvmStatic
    fun provideDatabase(context: Context): ZemogaDatabase {
        return Room.databaseBuilder(context, ZemogaDatabase::class.java, NAME).build()
    }

    @Provides
    @JvmStatic
    fun provideCommentsDao(appDatabase: ZemogaDatabase): CommentDao {
        return appDatabase.commentDao()
    }

    @Provides
    @JvmStatic
    fun provideUserDaoDao(appDatabase: ZemogaDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @JvmStatic
    fun providePostDaoDao(appDatabase: ZemogaDatabase): PostDao {
        return appDatabase.postDao()
    }

}