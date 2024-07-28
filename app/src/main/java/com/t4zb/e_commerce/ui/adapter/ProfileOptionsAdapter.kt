package com.t4zb.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.model.ProfileOption

class ProfileOptionsAdapter(private val options: List<ProfileOption>) :
    RecyclerView.Adapter<ProfileOptionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.ivOptionIcon)
        val title: TextView = itemView.findViewById(R.id.tvOptionTitle)
        val divider: View = itemView.findViewById(R.id.divider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.icon.setImageResource(option.icon)
        holder.title.text = option.title

        if (position == options.size - 1) {
            holder.divider.visibility = View.GONE
        } else {
            holder.divider.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = options.size
}