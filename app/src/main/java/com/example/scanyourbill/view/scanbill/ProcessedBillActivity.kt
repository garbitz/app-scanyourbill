package com.example.scanyourbill.view.scanbill

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.ListTransactionActivity
import com.example.scanyourbill.data.SaveBillRequest
import com.example.scanyourbill.data.response.BillResponse
import com.example.scanyourbill.data.response.toItemsMap
import com.example.scanyourbill.databinding.ActivityProcessedBillBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.main.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.UUID

class ProcessedBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProcessedBillBinding
    private val viewModel: BillViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    private lateinit var parentAdapter: BillParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProcessedBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progressBar = binding.progressBar


        val imageUri = intent.getStringExtra("imageUri")
        imageUri?.let {
            val uri = Uri.parse(it)
            showImage(uri)
            uploadImage(uri)
        }

        val parentRecyclerView: RecyclerView = binding.rvBillParent
        parentRecyclerView.layoutManager = LinearLayoutManager(this)
        parentAdapter = BillParentAdapter(emptyList(), null, this, viewModel)  // Initialize with null billDetails
        parentRecyclerView.adapter = parentAdapter

        viewModel.billResponse.observe(this) { response ->
            displayBillData(response)
            updateAdapterData(response)
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Bill has been created!", Toast.LENGTH_SHORT).show()

        }

        binding.submitBtn.setOnClickListener {
            val billResponse = viewModel.billResponse.value ?: return@setOnClickListener
            val saveBillRequest = convertBillResponseToSaveBillRequest(billResponse)
            viewModel.saveBill(saveBillRequest)
            Toast.makeText(this, "Bill has been saved!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ListTransactionActivity::class.java)
            startActivity(intent)
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                displayErrorDialog(it)
            }
        }
    }

    private fun convertBillResponseToSaveBillRequest(billResponse: BillResponse): SaveBillRequest {
        val items = billResponse.data?.scannedItems?.map { scannedItem ->
            scannedItem!!.toItemsMap().entries.map { (key, value) ->
                scannedItem.category to mapOf(key to value)
            }
        }?.flatten()?.associate { it } ?: emptyMap()

        val billDetails = billResponse.data?.billDetails?.let { billDetails ->
            mapOf(
                "billName" to billDetails.billName,
                "tax" to billDetails.tax,
                "serviceTax" to billDetails.serviceCharge,
                "discount" to billDetails.discount,
                "others" to billDetails.others,
                "grandTotal" to billDetails.grandTotal
            )
        } ?: emptyMap()

        return SaveBillRequest(
            billId = UUID.randomUUID().toString(),
            walletId = "e998546a-7abe-4f32-bfd1-20b5f83faafc",
            items = items,
            billDetails = billDetails
        )
    }

    private fun showImage(uri: Uri) {
        // binding.previewImageView.setImageURI(uri)
    }

    private fun uploadImage(uri: Uri) {
        viewModel.uploadBillImage(uri, applicationContext)
    }

    private fun displayBillData(billResponse: BillResponse) {
        // Display bill data if needed
    }

    private fun updateAdapterData(billResponse: BillResponse) {
        val transactions = billResponse.data?.scannedItems ?: emptyList()
        val billDetails = billResponse.data?.billDetails
        parentAdapter.updateData(transactions, billDetails)
    }

    private fun displayErrorDialog(errorMessage: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("Retry") { dialog, _ ->
                navigateToScanBillActivity()
                dialog.dismiss()
            }
            .setNeutralButton("Home") { dialog, _ ->
                navigateToMainActivity()
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun navigateToScanBillActivity() {
        val intent = Intent(this, ScanBillActivity::class.java)
        startActivity(intent)
    }
}





