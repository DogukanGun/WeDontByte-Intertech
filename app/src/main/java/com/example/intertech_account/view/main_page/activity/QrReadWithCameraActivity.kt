package com.example.intertech_account.view.main_page.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Size
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityQrReadWithCameraBinding
import com.example.intertech_account.view.main_page.fragment.qr_operation.BarcodeResultBottomSheetFragment
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback

 import android.view.View

import com.budiyev.android.codescanner.CodeScannerView










class QrReadWithCameraActivity : AppCompatActivity() {
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private var mCodeScanner: CodeScanner? = null
    private lateinit var binding: ActivityQrReadWithCameraBinding
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrReadWithCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123);
        } else {
            startScanning();
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show()
                startScanning()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun startScanning() {
        val scannerView = binding.scannerView
        mCodeScanner = CodeScanner(this, scannerView)
        mCodeScanner!!.setDecodeCallback(DecodeCallback { result ->
            runOnUiThread {
                Toast.makeText(this, result.text, Toast.LENGTH_SHORT).show()
            }
        })
        scannerView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                mCodeScanner!!.startPreview();
            }

        } )
    }
    override fun onResume() {
        super.onResume()
        if (mCodeScanner != null) {
            mCodeScanner!!.startPreview()
        }
    }

    override fun onPause() {
        if (mCodeScanner != null) {
            mCodeScanner!!.releaseResources()
        }
        super.onPause()
    }

}

//        checkCameraPermission()
//        cameraExecutor = Executors.newSingleThreadExecutor()
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener(Runnable {
//            val cameraProvider = cameraProviderFuture.get()
//            bindPreview(cameraProvider)
//        }, ContextCompat.getMainExecutor(this))

//    @SuppressLint ("Unsafe Experimental Usage Error")
//    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
//        val preview: Preview = Preview.Builder()
//            .build()
//        val cameraSelector: CameraSelector = CameraSelector.Builder()
//            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//            .build()
//        var previewView = binding.previewView
//        preview.setSurfaceProvider(
//            previewView.createSurfaceProvider(null)
//        )
//
//        val imageAnalysis = ImageAnalysis.Builder()
//            .setTargetResolution(Size(1280, 720))
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//            .build()
//        val analyzer=MyImageAnalyzer(supportFragmentManager)
//        imageAnalysis.setAnalyzer(cameraExecutor,analyzer)
//        cameraProvider.bindToLifecycle(
//            this as LifecycleOwner,
//            cameraSelector,
//            preview
//        )
//    }
//
//    private fun checkCameraPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Intent().also {
//                it.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                it.data = Uri.fromParts("package", packageName, null)
//                startActivity(it)
//                finish()
//            }
//        }
//    }
//}
//
//
//class MyImageAnalyzer(
//    private val fragmentManager: FragmentManager
//) : ImageAnalysis.Analyzer {
//
//    private var bottomSheet = BarcodeResultBottomSheetFragment()
//
//    override fun analyze(imageProxy: ImageProxy) {
//        scanBarcode(imageProxy)
//    }
//
//    @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
//    private fun scanBarcode(imageProxy: ImageProxy) {
//        imageProxy.image?.let { image ->
//            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
//            val scanner = BarcodeScanning.getClient()
//            scanner.process(inputImage)
//                .addOnCompleteListener {
//                    imageProxy.close()
//                    if (it.isSuccessful) {
//                        readBarcodeData(it.result as List<Barcode>)
//                    } else {
//                        it.exception?.printStackTrace()
//                    }
//                }
//        }
//    }
//
//    private fun readBarcodeData(barcodes: List<Barcode>) {
//        for (barcode in barcodes) {
//            when (barcode.valueFormat) {
//                Barcode.URL -> {
//                    if (!bottomSheet.isAdded)
//                        bottomSheet.binding.textViewLink.text= ""
//                    bottomSheet.updateURL(barcode.url?.url.toString())
//                }
//            }
//        }
//    }

