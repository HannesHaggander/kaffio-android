package com.towerowl.kaffio.data

import android.util.Log
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
        // return if the user is not allowed to change the group settings
        if (user.id != creatorId) return
        // return if the new limit is less than current members in group
        if (limit < mMembers.size) return
        groupLimit = limit
    }

    fun addMember(user: User): Boolean =
        if (groupLimit >= 0 && mMembers.size >= groupLimit || mMembers.contains(user)) false
        else mMembers.add(user)

    fun removeMember(user: User) = mMembers.remove(user)

}