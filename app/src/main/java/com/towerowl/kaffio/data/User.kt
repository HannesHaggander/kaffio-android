package com.towerowl.kaffio.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.towerowl.kaffio.enums.Roles
import java.util.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val role: Roles = Roles.User,
    var name: String
)