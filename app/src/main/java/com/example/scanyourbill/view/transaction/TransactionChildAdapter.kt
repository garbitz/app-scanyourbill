package com.example.scanyourbill.view.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.ActivitiesItem

class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val categoryTextView: TextView = view.findViewById(R.id.category)
    val amountTextView: TextView = view.findViewById(R.id.amount)
}

class TransactionChildAdapter(private val activities: List<ActivitiesItem?>) : RecyclerView.Adapter<ChildViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_child, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val activity = activities[position]
        holder.categoryTextView.text = activity?.category
        holder.amountTextView.text = activity?.amount.toString()
    }

    override fun getItemCount(): Int = activities.size
}