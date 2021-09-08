package com.example.intertech_account.view.main_page.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.ContextCompat
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentSettingBinding
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.view.main_page.activity.MainActivity


class SettingFragment : Fragment() {

    private lateinit var binding:FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSettingBinding.inflate(layoutInflater)

        binding.radioGroupLanguage.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioButtonTurkish)
            {
                binding.radioButtonTurkish.background = ContextCompat.getDrawable(binding.radioButtonTurkish.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonEnglish.background = ContextCompat.getDrawable(binding.radioButtonEnglish.context, R.drawable.qr_radio_button_not_selected)
                Button.isTurkishLanguageButtonClick.value = 1
            }
            else if (checkedId == R.id.radioButtonEnglish) {
                binding.radioButtonEnglish.background = ContextCompat.getDrawable(binding.radioButtonEnglish.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonTurkish.background = ContextCompat.getDrawable(binding.radioButtonTurkish.context, R.drawable.qr_radio_button_not_selected)
                Button.isEnglishLanguageButtonClick.value = 1
            }
        }

        binding.radioGroupTheme.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioButtonDark)
            {
                binding.radioButtonDark.background = ContextCompat.getDrawable(binding.radioButtonDark.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonLight.background = ContextCompat.getDrawable(binding.radioButtonLight.context, R.drawable.qr_radio_button_not_selected)
                Button.isDarkModeButtonClick.value = 1
            }
            else if (checkedId == R.id.radioButtonLight) {
                binding.radioButtonLight.background = ContextCompat.getDrawable(binding.radioButtonLight.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonDark.background = ContextCompat.getDrawable(binding.radioButtonDark.context, R.drawable.qr_radio_button_not_selected)
                Button.isLightModeButtonClick.value = 1
            }
        }


        return binding.root
    }



}