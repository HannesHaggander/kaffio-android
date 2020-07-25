package com.towerowl.kaffio.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.towerowl.kaffio.R
import kotlinx.android.synthetic.main.item_coffe_event.view.*
import java.util.*

class EventItemAdapter(private val onClick: (EventItemData) -> Unit) :
    RecyclerView.Adapter<EventItemViewHolder>() {

    private var mFilteredData: List<EventItemData> = mutableListOf()
    private var mAllData: List<EventItemData> = mutableListOf()
    val data: List<EventItemData> get() = mFilteredData

    fun updateData(newItems: List<EventItemData>, notify: Boolean = true) {
        mAllData = newItems
        mFilteredData = newItems
        if (notify) notifyDataSetChanged()
    }

    fun filter(onFilter: (EventItemData) -> Boolean, notify: Boolean = true) {
        mFilteredData = mAllData.filter { onFilter(it) }
        if (notify) notifyDataSetChanged()
    }

    fun resetFilter(notify: Boolean = true) {
        mFilteredData = mAllData
        if (notify) notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffe_event, parent, false)
            .run { EventItemViewHolder(this) }

    override fun getItemCount(): Int = mFilteredData.size

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        mFilteredData[position].run {
            holder.itemView.event_item_title.text = title
            holder.itemView.setOnClickListener { onClick(this) }
        }
    }

}

class EventItemViewHolder(v: View) : RecyclerView.ViewHolder(v)

data class EventItemData(val title: String, val creatorId: UUID)