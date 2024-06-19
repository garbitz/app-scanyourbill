package com.example.scanyourbill.view.scanbill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.ActivitiesItem
import com.example.scanyourbill.data.response.BillItem

class BillChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val notesTextView: TextView = view.findViewById(R.id.product_note)
    val amountTextView: TextView = view.findViewById(R.id.amount)
    val editButton: ImageView = view.findViewById(R.id.edit_btn)
}

interface BillChildAdapterListener {
    fun onItemUpdated(newTitle: String, newPrice: Int, position: Int)
}

class BillChildAdapter(
    private var activities: List<BillItem?>,
    private val supportFragmentManager: FragmentManager,
    private val viewModel: BillViewModel
) : RecyclerView.Adapter<BillChildViewHolder>(), BillChildAdapterListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_child, parent, false)
        return BillChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillChildViewHolder, position: Int) {
        val activity = activities[position]
        holder.notesTextView.text = activity?.title
        holder.amountTextView.text = activity?.price.toString()

        holder.editButton.setOnClickListener {
            val fragment = EditBillItemFragment().apply {
                arguments = Bundle().apply {
                    putString("title", activity?.title)
                    putInt("price", activity?.price ?: 0)
                    putInt("position", position) // Pass the position of the item
                }
                adapter = this@BillChildAdapter // Pass the adapter instance
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }




    }

    override fun getItemCount(): Int = activities.size

    override fun onItemUpdated(newTitle: String, newPrice: Int, position: Int) {
        // Create a new list with the updated item
        val updatedActivities = activities.toMutableList().apply {
            this[position] = this[position]?.copy(title = newTitle, price = newPrice)
        }
        // Update the adapter's data with the new list
        updateData(updatedActivities)

        viewModel.updateBillResponse(updatedActivities)

    }

    fun updateData(newActivities: List<BillItem?>) {
        activities = newActivities
        notifyDataSetChanged()
    }
}





