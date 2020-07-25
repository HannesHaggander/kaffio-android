package com.towerowl.kaffio.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.towerowl.kaffio.App
import com.towerowl.kaffio.R
import com.towerowl.kaffio.adapters.EventItemAdapter
import com.towerowl.kaffio.adapters.EventItemData
import kotlinx.android.synthetic.main.events_fragment.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsFragment : Fragment() {

    companion object {
        private const val TAG = "EventsFragment"
    }

    private val eventsRepository by lazy { App.dagger.eventsViewModel() }
    private val eventsAdapter by lazy {
        EventItemAdapter {
            Log.d(TAG, "EventItemRecyclerAdapter: Item pressed ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.events_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        events_recycler.run {
            adapter = eventsAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        lifecycleScope.launch(IO) {
            eventsRepository.getEvents().run {
                withContext(Main) {
                    eventsAdapter.updateData(map {
                        EventItemData(it.name, it.creatorId)
                    })
                }
            }
        }
    }

}