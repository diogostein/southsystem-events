package com.codelabs.southsystem.eventos.features.events.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.southsystem.eventos.R
import com.codelabs.southsystem.eventos.core.helpers.GlideHelper
import com.codelabs.southsystem.eventos.core.utils.format
import com.codelabs.southsystem.eventos.core.utils.toBrazilianCurrency
import com.codelabs.southsystem.eventos.databinding.ItemEventBinding
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event

class EventListAdapter(
    private val events: List<Event>,
    private val onItemClick: (Event) -> Unit,
) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_event, parent, false).let {
                EventViewHolder(it)
            }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.setOnItemClick { onItemClick(event) }
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemEventBinding.bind(itemView)

        fun bind(event: Event) {
            binding.tvTitle.text = event.title
            binding.tvDate.text = event.date.format()
            binding.tvPrice.text = event.price.toBrazilianCurrency()

            GlideHelper.load(event.image, binding.imageView)
        }

        fun setOnItemClick(onItemClick: (View) -> Unit) {
            itemView.setOnClickListener(onItemClick)
        }
    }

}
