// OfferListAdapter.kt
package com.example.aviatickets.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aviatickets.R
import com.example.aviatickets.databinding.ItemOfferBinding
import com.example.aviatickets.model.entity.Offer

class OfferListAdapter : ListAdapter<Offer, OfferListAdapter.ViewHolder>(OfferDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("OfferListAdapter", "Creating ViewHolder")
        return ViewHolder(
            ItemOfferBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun sortByPrice() {
        submitList(currentList.sortedBy { it.price })
    }

    fun sortByDuration() {
        submitList(currentList.sortedBy { it.flight.duration })
    }

    inner class ViewHolder(private val binding: ItemOfferBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(offer: Offer) {
            val flight = offer.flight
            val context = itemView.context // Obtain the context from the itemView

            with(binding) {
                // Add your binding logic here
                departureTime.text = flight.departureTimeInfo
                arrivalTime.text = flight.arrivalTimeInfo
                route.text = context.getString(
                    R.string.route_fmt,
                    flight.departureLocation.code,
                    flight.arrivalLocation.code
                )
                duration.text = context.getString(
                    R.string.time_fmt,
                    getTimeFormat(flight.duration).first.toString(),
                    getTimeFormat(flight.duration).second.toString()
                )
                direct.text = context.getString(R.string.direct)
                price.text = context.getString(R.string.price_fmt, offer.price.toString())
            }
        }

        private fun getTimeFormat(minutes: Int): Pair<Int, Int> = Pair(
            first = minutes / 60,
            second = minutes % 60
        )
    }



    private class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }
    }
}
