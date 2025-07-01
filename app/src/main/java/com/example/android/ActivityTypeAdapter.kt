package com.example.android

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import android.graphics.Color

class ActivityTypeAdapter(
    private val items: List<ActivityType>,
    private val onClick: (ActivityType) -> Unit
) : RecyclerView.Adapter<ActivityTypeAdapter.ViewHolder>() {

    data class ActivityType(val name: String, val iconRes: Int)

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.itemView.isSelected = position == selectedPosition
        val cardView = holder.itemView as CardView
        if (position == selectedPosition) {
            cardView.setCardBackgroundColor(Color.parseColor("#FFBB86FC")) // фиолетовый
        } else {
            cardView.setCardBackgroundColor(Color.WHITE)
        }
        holder.itemView.setOnClickListener {
            val prev = selectedPosition
            selectedPosition = position
            notifyItemChanged(prev)
            notifyItemChanged(position)
            onClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.activity_type_name)
    }
} 