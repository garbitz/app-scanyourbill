package com.example.scanyourbill.view.scanbill

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.BillResponse
import com.example.scanyourbill.databinding.ActivityProcessedBillBinding
import com.example.scanyourbill.view.ViewModelFactory

class ProcessedBillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProcessedBillBinding
    private val viewModel: BillViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

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

        // Get the image URI from the intent
        val imageUri = intent.getStringExtra("imageUri")
        imageUri?.let {
            val uri = Uri.parse(it)
            showImage(uri)
            uploadImage(uri)
        }

        viewModel.billResponse.observe(this) { response ->
            // Handle the response here
            displayBillData(response)
        }
    }

    private fun showImage(uri: Uri) {
//        binding.previewImageView.setImageURI(uri)
    }

    private fun uploadImage(uri: Uri) {
        viewModel.uploadBillImage(uri, applicationContext)
    }

    private fun displayBillData(billResponse: BillResponse) {
//        binding.billNameTextView.text = billResponse.data?.billDetails?.billName
//        binding.grandTotalTextView.text = billResponse.data?.billDetails?.grandTotal.toString()
//
//        // Example of how to display scanned items
//        val scannedItemsAdapter = ScannedItemsAdapter(billResponse.data?.scannedItems)
//        binding.scannedItemsRecyclerView.adapter = scannedItemsAdapter
//        binding.scannedItemsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
