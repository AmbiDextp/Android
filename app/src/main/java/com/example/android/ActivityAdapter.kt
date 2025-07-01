package com.example.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(
    private val tabType: String,
    private val onActivityClick: (ActivityItem.Activity) -> Unit
) : ListAdapter<ActivityAdapter.ActivityItem, RecyclerView.ViewHolder>(DiffCallback()) {

    sealed class ActivityItem {
        data class DateSection(val date: String) : ActivityItem()
        data class Activity(
            val ago: String,
            val title: String,
            val distance: String,
            val duration: String
        ) : ActivityItem()
    }

    class DiffCallback : DiffUtil.ItemCallback<ActivityItem>() {
        override fun areItemsTheSame(oldItem: ActivityItem, newItem: ActivityItem): Boolean {
            return when {
                oldItem is ActivityItem.DateSection && newItem is ActivityItem.DateSection ->
                    oldItem.date == newItem.date
                oldItem is ActivityItem.Activity && newItem is ActivityItem.Activity ->
                    oldItem.title == newItem.title && oldItem.ago == newItem.ago
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ActivityItem, newItem: ActivityItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ActivityItem.DateSection -> VIEW_TYPE_DATE
            is ActivityItem.Activity -> VIEW_TYPE_ACTIVITY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATE -> DateSectionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date_section, parent, false)
            )
            VIEW_TYPE_ACTIVITY -> ActivityViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_activity, parent, false),
                onActivityClick
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ActivityItem.DateSection -> (holder as DateSectionViewHolder).bind(item)
            is ActivityItem.Activity -> (holder as ActivityViewHolder).bind(item, tabType)
        }
    }

    companion object {
        private const val VIEW_TYPE_DATE = 0
        private const val VIEW_TYPE_ACTIVITY = 1
    }
}

class DateSectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateText: TextView = view.findViewById(R.id.date_text)

    fun bind(item: ActivityAdapter.ActivityItem.DateSection) {
        dateText.text = item.date
    }
}

class ActivityViewHolder(
    view: View,
    private val onActivityClick: (ActivityAdapter.ActivityItem.Activity) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val titleText: TextView = view.findViewById(R.id.title_text)
    private val agoText: TextView = view.findViewById(R.id.ago_text)
    private val durationText: TextView = view.findViewById(R.id.duration_text)
    private val distanceText: TextView = view.findViewById(R.id.distance_text)

    fun bind(item: ActivityAdapter.ActivityItem.Activity, tabType: String) {
        titleText.text = item.title
        agoText.text = item.ago
        durationText.text = item.duration
        distanceText.text = item.distance

        itemView.setOnClickListener { onActivityClick(item) }
    }
}