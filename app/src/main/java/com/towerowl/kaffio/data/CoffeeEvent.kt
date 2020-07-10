package com.towerowl.kaffio.data

import java.util.*

data class CoffeeEvent(
    val id: UUID = UUID.randomUUID()
) {
    private val mMembers: MutableList<User> = mutableListOf()
    val members: List<User> get() = mMembers

    fun addMember(user: User) {

        mMembers.add(user)
    }

    fun removeMember(user: User) {
        mMembers.remove(user)
    }
}