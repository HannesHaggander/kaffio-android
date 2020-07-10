package com.towerowl.kaffio.data

import java.util.*

data class CoffeeEvent(
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var groupLimit: Int = -1
) {
    private val mMembers = mutableListOf<User>()
    val members: List<User> get() = mMembers.toList()

    fun addMember(user: User): Boolean =
        if (groupLimit >= 0 && members.size >= groupLimit || mMembers.contains(user)) false
        else mMembers.add(user)

    fun removeMember(user: User) = mMembers.remove(user)

}