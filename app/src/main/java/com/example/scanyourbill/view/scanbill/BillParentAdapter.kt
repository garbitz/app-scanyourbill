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
import com.example.scanyourbill.data.response.BillDetails
import com.example.scanyourbill.data.response.ScannedItemsItem

class BillParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val catTextView: TextView = view.findViewById(R.id.category)
    val childRecyclerView: RecyclerView = view.findViewById(R.id.rvBillChild)

}

class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val billTextView: TextView = view.findViewById(R.id.bill_name)
    val taxTextView: TextView = view.findViewById(R.id.tax)
    val serviceTaxTextView: TextView = view.findViewById(R.id.service_tax)
    val discountTextView: TextView = view.findViewById(R.id.discount)
    val othersTextView: TextView = view.findViewById(R.id.others)
    val totalTextView: TextView = view.findViewById(R.id.total_val)
}

class BillParentAdapter(
    var transactions: List<ScannedItemsItem?>,
    private var billDetails: BillDetails?, // Include BillDetails in the constructor
    private val activity: AppCompatActivity,
    private val viewModel: BillViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_FOOTER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == transactions.size) VIEW_TYPE_FOOTER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_parent, parent, false)
            BillParentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.bill_footer, parent, false)
            FooterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BillParentViewHolder) {
            val transaction = transactions[position]
            holder.catTextView.text = transaction?.category
            holder.childRecyclerView.apply {
                layoutManager = LinearLayoutManager(holder.itemView.context)
                adapter = BillChildAdapter(transaction?.items ?: listOf(), activity.supportFragmentManager, viewModel)
            }
            // Bind BillDetails data to the TextViews

        } else if (holder is FooterViewHolder) {
            // Bind footer content here if needed
            holder.billTextView.text = billDetails?.billName ?: "N/A"
            holder.taxTextView.text = billDetails?.tax?.toString() ?: "N/A"
            holder.serviceTaxTextView.text = billDetails?.serviceCharge?.toString() ?: "N/A"
            holder.discountTextView.text = billDetails?.discount?.toString() ?: "N/A"
            holder.othersTextView.text = billDetails?.others?.toString() ?: "N/A"
            holder.totalTextView.text = billDetails?.grandTotal?.toString() ?: "N/A"
        }
    }

    override fun getItemCount(): Int = transactions.size + 1

    fun updateData(newTransactions: List<ScannedItemsItem?>?, newBillDetails: BillDetails?) {
        transactions = newTransactions ?: listOf()
        billDetails = newBillDetails
        notifyDataSetChanged()
    }
}





