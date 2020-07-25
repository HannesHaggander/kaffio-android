package com.towerowl.kaffio.repositories

import com.towerowl.kaffio.data.CoffeeEvent

class EventsRepository {

    suspend fun getEvents(): List<CoffeeEvent> {
        return listOf()
    }

}