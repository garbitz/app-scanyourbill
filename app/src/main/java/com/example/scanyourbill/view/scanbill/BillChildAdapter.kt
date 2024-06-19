package com.example.scanyourbill.view.scanbill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.ActivitiesItem

class BillChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val categoryTextView: TextView = view.findViewById(R.id.category)
    val amountTextView: TextView = view.findViewById(R.id.amount)
}

class BillChildAdapter(private val activities: List<ActivitiesItem?>) : RecyclerView.Adapter<BillChildViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_child, parent, false)
        return BillChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillChildViewHolder, position: Int) {
        val activity = activities[position]
        holder.categoryTextView.text = activity?.category
        holder.amountTextView.text = activity?.amount.toString()
    }

    override fun getItemCount(): Int = activities.size
}