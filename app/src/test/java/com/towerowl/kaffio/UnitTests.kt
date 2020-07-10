package com.towerowl.kaffio

import com.towerowl.kaffio.data.CoffeeEvent
import com.towerowl.kaffio.data.User
import org.junit.Test

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
//    @Test
//    fun `check that added events show up in brewers list`() {
//
//    }

    //- verify safe handling of group limit reduction when more members are in

    /**
     * Check that users added to events are in the interest list
     */
    @Test
    fun `Check that users are added and removed from events`() {
        val event = CoffeeEvent(name = "test event")
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
        CoffeeEvent(name = "test event", groupLimit = limit - 1).run {
            for (i in 0 until limit) {
                addMember(User(name = "user:$i"))
            }

            assert(members.size < limit)
        }
    }

    @Test
    fun `Verify no duplicates users in event`(){
        val event = CoffeeEvent(name = "test event")
        User(name = "test user").run {
            event.addMember(this)
            event.addMember(this)
        }
        assert(event.members.size == 1)
    }


    //- control that only the creator and admin can change an event
    //- control that users are informed about updates to interested event

}