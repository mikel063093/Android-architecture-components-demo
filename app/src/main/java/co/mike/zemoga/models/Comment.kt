package co.mike.zemoga.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
class Comment(@field:PrimaryKey
              val id: String, val postId: Int, val name: String, val email: String, val body: String)