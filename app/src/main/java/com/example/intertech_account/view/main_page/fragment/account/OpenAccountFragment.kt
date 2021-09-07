package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentOpenAccountBinding

class OpenAccountFragment : Fragment() {

    lateinit var binding: FragmentOpenAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOpenAccountBinding.inflate(layoutInflater)

        var AccountName: String
        val spinnerList: ArrayList<String> = arrayListOf<String>()

        spinnerList.add("TRY")
        spinnerList.add("USD")
        spinnerList.add("EUR")
        spinnerList.add("GBP")
        spinnerList.add("CAD")

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerList
        )
        binding.currencySpinner.adapter = adapter
        binding.currencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            }



        return binding.root
    }

}