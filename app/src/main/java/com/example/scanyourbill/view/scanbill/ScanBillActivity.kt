package com.example.scanyourbill.view.scanbill

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scanyourbill.R
import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.repository.BillRepository
import com.example.scanyourbill.databinding.ActivityScanBillBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.transaction.TransactionViewModel

class ScanBillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBillBinding
    private var currentImageUri: Uri? = null
    private var selectedWalletId: String? = null // Add this line

    private val viewModel: TransactionViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScanBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryBtn.setOnClickListener { startGallery() }
        binding.cameraBtn.setOnClickListener { startCamera() }

        val listPopupWindowButton = binding.dropdownWallet
        val listPopupWindow = ListPopupWindow(this, null, com.google.android.material.R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton

        // Observe wallet list changes
        viewModel.walletList.observe(this) { walletDataList ->
            val items = walletDataList.map { it.walletName ?: "Unknown" }
            val adapter = ArrayAdapter(this, R.layout.list_popup_window_wallet, items)
            listPopupWindow.setAdapter(adapter)
        }

        // Set list popup's item click listener
        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            // Respond to list popup window item click.
            val selectedItem = viewModel.walletList.value?.get(position)?.walletName ?: "Unknown"
            selectedWalletId = viewModel.walletList.value?.get(position)?.walletId // Get the wallet ID
            binding.wallet.text = selectedItem
            // Dismiss popup.
            listPopupWindow.dismiss()
        }

        // Show list popup window on button click.
        listPopupWindowButton.setOnClickListener { v: View? -> listPopupWindow.show() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
            navigateToProcessedBillActivity()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
            navigateToProcessedBillActivity()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
//            binding.previewImageView.setImageURI(it)
        }
    }

    private fun navigateToProcessedBillActivity() {
        currentImageUri?.let {
            val intent = Intent(this, ProcessedBillActivity::class.java)
            intent.putExtra("imageUri", it.toString())
            intent.putExtra("walletId", selectedWalletId) // Pass the selected wallet ID
            startActivity(intent)
        }
    }
}


