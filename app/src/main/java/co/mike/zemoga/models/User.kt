package co.mike.zemoga.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(
        @field:PrimaryKey
        val id: String,
        val name: String,
        val username: String,
        val phone: String,
        val website: String
)