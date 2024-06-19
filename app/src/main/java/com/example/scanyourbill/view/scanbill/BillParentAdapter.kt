package com.example.scanyourbill.view.scanbill

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.DataItemTransaction
import com.example.scanyourbill.view.transaction.TransactionChildAdapter

class BillParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val catTextView: TextView = view.findViewById(R.id.category)
    val dateTextView: TextView = view.findViewById(R.id.date)
    val totalTextView: TextView = view.findViewById(R.id.total)
    val childRecyclerView: RecyclerView = view.findViewById(R.id.rvBillChild)
}

class BillParentAdapter(private var transactions: List<DataItemTransaction?>) : RecyclerView.Adapter<BillParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_parent, parent, false)
        return BillParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillParentViewHolder, position: Int) {
        val transaction = transactions[position]
        Log.d("TransactionAdapter", "Binding transaction: $transaction")

        holder.dateTextView.text = transaction?.date
        holder.totalTextView.text = transaction?.rangeSummary.toString()
        holder.childRecyclerView.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = TransactionChildAdapter(transaction?.activities ?: listOf())
        }
    }

    override fun getItemCount(): Int = transactions.size

    // Method to update the data
    fun updateData(newTransactions: List<DataItemTransaction?>) {
        transactions = newTransactions
        Log.d("TransactionAdapter", "Updating data: $newTransactions")

        notifyDataSetChanged()
    }
}