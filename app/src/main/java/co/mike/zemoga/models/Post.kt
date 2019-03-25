package co.mike.zemoga.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "posts")
class Post(@field:PrimaryKey
           val id: String,
           val userId: Int,
           val title: String,
           val body: String,
           val read: Boolean,
           val favorite: Boolean)