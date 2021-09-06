package com.example.intertech_account.view.main_page.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentSettingBinding
import com.example.intertech_account.resources.common_variables.Button


class SettingFragment : Fragment() {

    private lateinit var binding:FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSettingBinding.inflate(layoutInflater)
        binding.radioButtonTurkish.setOnClickListener{
                 Button.isTurkishLanguageButtonClick.value=1

        }
        binding.radioButtonEnglish.setOnClickListener {
            Button.isEnglishLanguageButtonClick.value=1

        }
        binding.radioButtonDark.setOnClickListener{
            Button.isDarkModeButtonClick.value=1
        }
        binding.radioButtonLight.setOnClickListener {
            Button.isLightModeButtonClick.value=1

        }


        return binding.root
    }



}