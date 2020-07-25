package com.towerowl.kaffio.models

import androidx.lifecycle.ViewModel
import com.towerowl.kaffio.data.CoffeeEvent
import com.towerowl.kaffio.repositories.EventsRepository

class EventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    suspend fun getEvents(): List<CoffeeEvent> {
        return eventsRepository.getEvents()
    }

}