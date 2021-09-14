package com.example.intertech_account.view.main_page.fragment.pdf

import android.Manifest.permission
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.intertech_account.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.Manifest.permission.READ_EXTERNAL_STORAGE

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.graphics.*
import android.os.Build
import androidx.test.core.app.ApplicationProvider

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import android.widget.Toast
import com.example.intertech_account.databinding.FragmentPdfCreateAndDownloadBinding


class PdfCreateAndDownloadFragment : Fragment() {


    // variables for our buttons.
    var generatePDFbtn: Button? = null

    // declaring width and height
    // for our PDF file.
    var pageHeight = 1120
    var pagewidth = 792

    // creating a bitmap variable
    // for storing our images
    var bmp: Bitmap? = null
    var scaledbmp: // creating a bitmap variable
    // for storing our images
    Bitmap? = null

    private lateinit var binding:FragmentPdfCreateAndDownloadBinding
    private val PERMISSION_REQUEST_CODE = 200
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // initializing our variables.
        binding= FragmentPdfCreateAndDownloadBinding.inflate(layoutInflater)
        generatePDFbtn = binding.createPdf
        bmp = BitmapFactory.decodeResource(resources, R.drawable.back);
        scaledbmp = Bitmap.createScaledBitmap(bmp!!, 140, 140, false);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        generatePDFbtn!!.setOnClickListener {
            // generate our PDF file.
            generatePDF()
        }
        return binding.root
    }


    private fun checkPermission(): Boolean {
        // checking of permissions.
        val permission1 =
            ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Toast.makeText(requireContext(), "Permission Granted..", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Permission Denined.", Toast.LENGTH_SHORT).show()
                 }
            }
        }
    }
    private fun generatePDF() {
        // creating an object variable
        // for our PDF document.
        val pdfDocument = PdfDocument()

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        val paint = Paint()
        val title = Paint()

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        val mypageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(
            pagewidth,
            pageHeight,
            1
        ).create()

        // below line is used for setting
        // start page for our PDF file.
        val myPage: PdfDocument.Page = pdfDocument.startPage(mypageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        val canvas: Canvas = myPage.getCanvas()

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp!!, 56F, 40F, paint)

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(15F)

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(requireContext(), android.R.color.holo_purple))

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
        canvas.drawText("Geeks for Geeks", 209F, 80F, title)

        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(requireContext(), android.R.color.holo_purple))
        title.setTextSize(15F)

        // below line is used for setting
        // our text to center of PDF.
        title.textAlign = Paint.Align.CENTER
        canvas.drawText("This is sample document which we have created.", 396F, 560F, title)

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.
        var file:File
        if (Build.VERSION.SDK_INT>29){
            file=File(Environment.getStorageDirectory().toString() + "/", "GFG.pdf")
        }else{
            file=File(Environment.getExternalStorageDirectory().toString() + "/", "GFG.pdf")
        }
        file.mkdirs()

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))


        } catch (e: IOException) {
            // below line is used
            // to handle error
            e.printStackTrace()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }
}