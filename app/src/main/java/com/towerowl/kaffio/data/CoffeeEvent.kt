package com.towerowl.kaffio.data

import java.util.*

data class CoffeeEvent(
    val id: UUID = UUID.randomUUID(),
    var name: String
) {
    private val mMembers = mutableListOf<User>()
    val members: List<User> get() = mMembers.toList()

    var groupLimit: Int = -1
        set(value) {
            if (value < mMembers.size) members.forEach {
                if (value < mMembers.size) removeMember(it)
            }
            field = value
        }

    fun addMember(user: User): Boolean =
        if (groupLimit >= 0 && mMembers.size >= groupLimit || mMembers.contains(user)) false
        else mMembers.add(user)

    fun removeMember(user: User) = mMembers.remove(user)

}