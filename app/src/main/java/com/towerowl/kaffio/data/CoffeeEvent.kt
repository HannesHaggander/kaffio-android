package com.towerowl.kaffio.data

import java.util.*

data class CoffeeEvent(
    val id: UUID = UUID.randomUUID(),
    val creatorId: UUID,
    var name: String
) {

    init {
        if (name.isEmpty()) throw Exception("Name of event can not be empty")
    }

    private val mMembers = mutableListOf<User>()
    val members: List<User> get() = mMembers.toList()
    var groupLimit: Int = -1
        private set

    fun setGroupLimit(user: User, limit: Int) {
        if(user.id != creatorId) return
        if (limit < mMembers.size)
            members.take(members.size - limit).forEach { removeMember(it) }
        groupLimit = limit
    }

    fun addMember(user: User): Boolean =
        if (groupLimit >= 0 && mMembers.size >= groupLimit || mMembers.contains(user)) false
        else mMembers.add(user)

    fun removeMember(user: User) = mMembers.remove(user)

}