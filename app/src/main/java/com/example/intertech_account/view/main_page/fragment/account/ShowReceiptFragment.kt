package com.example.intertech_account.view.main_page.fragment.account

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentSimpleAccountBinding
import com.example.intertech_account.databinding.SimpleAccountRecyclerviewRowBinding

class ShowReceiptFragment : Fragment() {

    lateinit var binding: SimpleAccountRecyclerviewRowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_receipt, container, false)
    }


}