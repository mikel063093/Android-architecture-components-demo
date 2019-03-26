package co.mike.zemoga.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "posts")
class Post(@field:PrimaryKey
           val id: String,
           @SerializedName("userId") val userId: String,
           val title: String,
           val body: String,
           val read: Boolean,
           val favorite: Boolean)