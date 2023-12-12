package com.example.rekanikan.view.fishscan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.rekanikan.R
import com.example.rekanikan.Utils.getImageUri
import com.example.rekanikan.Utils.reduceFileImage
import com.example.rekanikan.Utils.uriToFile
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.data.result.Result
import com.example.rekanikan.databinding.ActivityFishScanBinding
import com.example.rekanikan.view.camera.CameraActivity
import com.example.rekanikan.view.camera.CameraActivity.Companion.CAMERAX_RESULT
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


class FishScanActivity : AppCompatActivity() {
    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityFishScanBinding

    private val viewModel by viewModels<FishScanViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFishScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()){
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.cameraBtn.setOnClickListener {
            startCamera()
        }
        binding.scanBtn.isEnabled = false
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }




    private fun startCameraX(){
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
            binding.scanBtn.setOnClickListener {
                scanImage()
            }
        }
    }

    private fun showImage(){
        currentImageUri?.let {
            Log.d("Image URI check", "$it")
            binding.imagePreview.setImageURI(it)
            binding.scanBtn.isEnabled = true
            binding.scanBtn.alpha = 1f
        } ?: {
            binding.scanBtn.isEnabled = false
            binding.scanBtn.alpha = 0.5f
        }
    }

    private fun scanImage(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("check uri path", "show imaghe from ${uri.path}")

            viewModel.scanFish(imageFile).observe(this) {result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            Toast.makeText(this, "${result.data.namaIkan} : ${result.data.caraPerawatan}", Toast.LENGTH_SHORT).show()
                        }

                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, "${result.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        } ?: { Toast.makeText(this, "image empty", Toast.LENGTH_SHORT).show()}
    }

    private fun showLoading(isLoading: Boolean){
        binding.loadingLoop.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}