package com.example.intertech_account.view.main_page.fragment.qr_operation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentBarcodeResultBottomSheetBinding
import org.jsoup.Jsoup
import java.util.concurrent.Executors

class BarcodeResultBottomSheetFragment : Fragment() {

    lateinit var binding:FragmentBarcodeResultBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBarcodeResultBottomSheetBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_barcode_result_bottom_sheet, container, false)
    }

    // TODO ?

    fun updateURL(url: String) {
        fetchUrlMetaData(url) { title, desc ->
            view?.apply {
                binding.textViewTitle.text = title
                binding.textViewDesc.text = desc
                binding.textViewLink.text = url
                binding.textViewVisitLink.setOnClickListener { _ ->
                    Intent(Intent.ACTION_VIEW).also {
                        it.data = Uri.parse(url)
                        startActivity(it)
                    }
                }
            }
        }
    }

    //this function will fetch URL data
    private fun fetchUrlMetaData(
        url: String,
        callback: (title: String, desc: String) -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val doc = Jsoup.connect(url).get()
            val desc = doc.select("meta[name=description]")[0].attr("content")
            handler.post {
                callback(doc.title(), desc)
            }
        }
    }

}