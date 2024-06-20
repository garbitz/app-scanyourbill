package com.example.scanyourbill.view.transaction

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.DataItemTransaction
import com.example.scanyourbill.extractDateInfo
import com.example.scanyourbill.formatCurrency

class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val dateNumber: TextView = view.findViewById(R.id.date_number)
    val dateDay: TextView = view.findViewById(R.id.date_day)
    val dateMonthYear: TextView = view.findViewById(R.id.date_monthYear)
    val totalTextView: TextView = view.findViewById(R.id.total)
    val childRecyclerView: RecyclerView = view.findViewById(R.id.rvTransactionChild)
}

class TransactionParentAdapter(private var transactions: List<DataItemTransaction?>) : RecyclerView.Adapter<ParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_parent, parent, false)
        return ParentViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val transaction = transactions[position]
        Log.d("TransactionAdapter", "Binding transaction: $transaction")

        val dateInfo = extractDateInfo(transaction?.date!!)


        holder.dateNumber.text = dateInfo["dateNumber"].toString()
        holder.dateDay.text = dateInfo["day"].toString()
        holder.dateMonthYear.text = dateInfo["monthYear"].toString()
        holder.totalTextView.text = formatCurrency("Rp", transaction.rangeSummary!!)
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

