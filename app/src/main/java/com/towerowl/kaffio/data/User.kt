package com.towerowl.kaffio.data

import com.towerowl.kaffio.enums.Roles
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val role: Roles = Roles.User,
    var name: String
)