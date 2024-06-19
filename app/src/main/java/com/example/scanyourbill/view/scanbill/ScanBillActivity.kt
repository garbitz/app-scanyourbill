package com.example.scanyourbill.view.scanbill

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScanBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryBtn.setOnClickListener { startGallery() }
        binding.cameraBtn.setOnClickListener { startCamera() }
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
            startActivity(intent)
        }
    }
}


