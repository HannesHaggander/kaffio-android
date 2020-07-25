package com.towerowl.kaffio.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.towerowl.kaffio.R
import kotlinx.android.synthetic.main.item_coffe_event.view.*

class EventItemRecyclerAdapter(private val onClick: (EventItemData) -> Unit) :
    RecyclerView.Adapter<EventItemViewHolder>() {

    private var mData: List<EventItemData> = mutableListOf()
    val data: List<EventItemData> get() = mData

    fun updateData(newItems: List<EventItemData>) {
        mData = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffe_event, parent, false)
            .run { EventItemViewHolder(this) }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        mData[position].run {
            holder.itemView.event_item_title.text = title
            holder.itemView.setOnClickListener { onClick(this) }
        }
    }

}

class EventItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}

data class EventItemData(val title: String)