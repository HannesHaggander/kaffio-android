package com.towerowl.kaffio

import com.towerowl.kaffio.data.CoffeeEvent
import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.enums.Roles
import org.junit.Assert
import org.junit.Test
import java.util.*

class UnitTests {

    /**
     * Verify changes in user role applied correctly for user object
     */
    @Test
    fun `verify changes in user role`() {
        assert((User(name = "a")).role == Roles.User)
        assert((User(name = "b", role = Roles.Admin)).role == Roles.Admin)
    }

//    /**
//     * Check that added events show up in brewers list
//     */

    /**
     * verify safe handling of group limit reduction when more members are in
     */
    @Test
    fun `validate members size if group limit lowers`() {
        val creator = User(name = "test user")
        val event = CoffeeEvent(creatorId = creator.id, name = "test event")
        for (i in 0 until 3) event.addMember(User(name = "user:$i"))
        event.setGroupLimit(creator, 1)
        assert(event.members.size == 3)
    }

    /**
     * Check that users added to events are in the interest list
     */
    @Test
    fun `Check that users are added and removed from events`() {
        val event = CoffeeEvent(name = "test event", creatorId = UUID.randomUUID())
        val userA = User(name = "Name a")
        event.addMember(userA)
        assert(event.members.contains(userA))
        event.removeMember(userA)
        assert(!event.members.contains(userA))
    }

    /**
     * Verify that users cant join a full event
     */
    @Test
    fun `Verify user can not join a full event`() {
        val limit = 5
        val creator = User(name = "test user")
        CoffeeEvent(creatorId = creator.id, name = "test event").run {
            setGroupLimit(creator, limit - 1)
            for (i in 0 until limit) {
                addMember(User(name = "user:$i"))
            }

            assert(members.size < limit)
        }
    }

    @Test
    fun `Verify non creator cannot change event`() {
        val creator = User(name = "test creator")
        val nonCreator = User(name = "test non creator")
        CoffeeEvent(creatorId = creator.id, name = "test event").run {
            setGroupLimit(creator, 5)
            setGroupLimit(nonCreator, 1)
            assert(groupLimit == 5)
        }
    }

    @Test
    fun `Verify no duplicates users in event`() {
        val event = CoffeeEvent(creatorId = UUID.randomUUID(), name = "test event")
        User(name = "test user").run {
            event.addMember(this)
            event.addMember(this)
        }
        assert(event.members.size == 1)
    }

    @Test
    fun `Verify crash on empty event name`() {
        try {
            CoffeeEvent(creatorId = UUID.randomUUID(), name = "")
            Assert.fail()
        } catch (e: Exception) {
        }
    }

    //- control that only the creator and admin can change an event


    //- control that users are informed about updates to interested event

}