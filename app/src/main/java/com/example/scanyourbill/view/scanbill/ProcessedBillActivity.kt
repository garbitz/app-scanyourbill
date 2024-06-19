package com.example.scanyourbill.view.scanbill

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.data.response.BillResponse
import com.example.scanyourbill.databinding.ActivityProcessedBillBinding
import com.example.scanyourbill.view.ViewModelFactory
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

        val imageUri = intent.getStringExtra("imageUri")
        imageUri?.let {
            val uri = Uri.parse(it)
            showImage(uri)
            uploadImage(uri)
        }

        viewModel.billResponse.observe(this) { response ->
            displayBillData(response)
        }

        val parentRecyclerView: RecyclerView = binding.rvBillParent
        parentRecyclerView.layoutManager = LinearLayoutManager(this)
        parentAdapter = BillParentAdapter(emptyList(), this, viewModel)
        parentRecyclerView.adapter = parentAdapter

        viewModel.billResponse.observe(this) { transactionResponse ->
            transactionResponse.data?.let {
                parentAdapter.updateData(it.scannedItems)
            }
        }

//        binding.submitBtn.setOnClickListener {
//            val billItems = parentAdapter.transactions.flatMap { scannedItem ->
//                scannedItem?.items ?: emptyList()
//            }
//
//            val billDetails = mapOf(
//                "billName" to "Bill-${System.currentTimeMillis()}",
//                "tax" to 0,
//                "serviceTax" to 0,
//                "discount" to 0,
//                "others" to 0,
//                "grandTotal" to billItems.sumOf { it?.price ?: 0 }
//            )
//
//            val items = billItems.groupBy { it?.title }.mapValues { (_, items) ->
//                items.associateWith { it?.price ?: 0 }
//            }
//
//            val billId = UUID.randomUUID().toString()
//            val walletId = "e998546a-7abe-4f32-bfd1-20b5f83faafc"
//
//            viewModel.saveBill(
//                billId = billId,
//                walletId = walletId,
//                items = items,
//                billDetails = billDetails
//            )
//        }
    }

    private fun showImage(uri: Uri) {
        // binding.previewImageView.setImageURI(uri)
    }

    private fun uploadImage(uri: Uri) {
        viewModel.uploadBillImage(uri, applicationContext)
    }

    private fun displayBillData(billResponse: BillResponse) {
        // Display bill data
    }
}



