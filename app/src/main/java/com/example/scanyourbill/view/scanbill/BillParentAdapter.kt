package com.example.scanyourbill.view.scanbill

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.ScannedItemsItem

class BillParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val catTextView: TextView = view.findViewById(R.id.category)
//    val totalTextView: TextView = view.findViewById(R.id.total)
//    val dateTextView: TextView = view.findViewById(R.id.date)
    val childRecyclerView: RecyclerView = view.findViewById(R.id.rvBillChild)
}

class BillParentAdapter(
    var transactions: List<ScannedItemsItem?>,
    private val activity: AppCompatActivity,
    private val viewModel: BillViewModel // Use AppCompatActivity here
) : RecyclerView.Adapter<BillParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_parent, parent, false)
        return BillParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillParentViewHolder, position: Int) {
        val transaction = transactions[position]
        Log.d("BillAdapter", "Binding transaction: $transaction")

        holder.catTextView.text = transaction?.category
        holder.childRecyclerView.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = BillChildAdapter(transaction?.items ?: listOf(), activity.supportFragmentManager, viewModel)
        }
    }

    override fun getItemCount(): Int = transactions.size

    fun updateData(newTransactions: List<ScannedItemsItem?>?) {
        transactions = newTransactions!!
        Log.d("TransactionAdapter", "Updating data: $newTransactions")
        notifyDataSetChanged()
    }
}


