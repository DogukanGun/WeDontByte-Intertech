package com.example.intertech_account.view.main_page.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intertech_account.databinding.ActivitySettingBinding
import com.example.intertech_account.databinding.FragmentSettingBinding
import com.example.intertech_account.resources.common_variables.Button

class SettingActivity : AppCompatActivity() {
    lateinit var binding:ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar =  binding.appToolbarSettingActivity
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
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

    }
}