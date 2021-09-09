package com.example.intertech_account.view.main_page.activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.intertech_account.BaseActivity
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivitySettingBinding
import com.example.intertech_account.databinding.FragmentSettingBinding
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.Preferences
import com.zeugmasolutions.localehelper.Locales
import java.util.*

class SettingActivity : BaseActivity() {
    lateinit var binding:ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar =  binding.appToolbarSettingActivity
        setSupportActionBar(toolbar)
        val language=rememberLanguage()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        binding.radioButtonTurkish.setOnClickListener{
//            Button.isTurkishLanguageButtonClick.value=1
//
//        }
//        binding.radioButtonEnglish.setOnClickListener {
//            Button.isEnglishLanguageButtonClick.value=1
//
//        }
//        binding.radioButtonDark.setOnClickListener{
//            Button.isDarkModeButtonClick.value=1
//        }
//        binding.radioButtonLight.setOnClickListener {
//            Button.isLightModeButtonClick.value=1
//

//        }
         if (Button.isTurkishLanguageButtonClick==-1){

             if (language=="Turkish"){
                 binding.radioButtonTurkish.isChecked=true
                 binding.radioButtonEnglish.isChecked=false

                 binding.radioButtonTurkish.background = ContextCompat.getDrawable(binding.radioButtonTurkish.context, R.drawable.qr_radio_button_selected)
                 binding.radioButtonEnglish.background = ContextCompat.getDrawable(binding.radioButtonEnglish.context, R.drawable.qr_radio_button_not_selected)
                 Button.isTurkishLanguageButtonClick=1
                 Button.isEnglishLanguageButtonClick=0
            }else{
                 binding.radioButtonEnglish.isChecked=true
                 binding.radioButtonTurkish.isChecked=false

                 binding.radioButtonEnglish.background = ContextCompat.getDrawable(binding.radioButtonEnglish.context, R.drawable.qr_radio_button_selected)
                 binding.radioButtonTurkish.background = ContextCompat.getDrawable(binding.radioButtonTurkish.context, R.drawable.qr_radio_button_not_selected)
                 Button.isEnglishLanguageButtonClick=1
                 Button.isTurkishLanguageButtonClick=0
            }
        }

//        binding.radioButtonTurkish.setOnClickListener {
//            if (Button.isTurkishLanguageButtonClick==0){
//                updateLocale(Locales.Turkish)
//                saveLanguage("Turkish")
//                binding.radioButtonTurkish.background = ContextCompat.getDrawable(binding.radioButtonTurkish.context, R.drawable.qr_radio_button_selected)
//                binding.radioButtonEnglish.background = ContextCompat.getDrawable(binding.radioButtonEnglish.context, R.drawable.qr_radio_button_not_selected)
//
//                Button.isTurkishLanguageButtonClick = 1
//                Button.isEnglishLanguageButtonClick=0
//            }
//
//        }
        binding.radioGroupLanguage.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioButtonTurkish )
            {
                binding.radioButtonTurkish.background = ContextCompat.getDrawable(binding.radioButtonTurkish.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonEnglish.background = ContextCompat.getDrawable(binding.radioButtonEnglish.context, R.drawable.qr_radio_button_not_selected)

                if ( Button.isTurkishLanguageButtonClick==0){
                    updateLocale(Locales.Turkish)
                    saveLanguage("Turkish")

                    Button.isTurkishLanguageButtonClick = 1
                    Button.isEnglishLanguageButtonClick=0
                }

            }
            else if (checkedId == R.id.radioButtonEnglish) {
                print("secim yapildi")
                binding.radioButtonEnglish.background = ContextCompat.getDrawable(binding.radioButtonEnglish.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonTurkish.background = ContextCompat.getDrawable(binding.radioButtonTurkish.context, R.drawable.qr_radio_button_not_selected)

                if (Button.isEnglishLanguageButtonClick==0){
                    Button.isEnglishLanguageButtonClick = 1
                    Button.isTurkishLanguageButtonClick=0
                    updateLocale(Locales.English)
                    saveLanguage("English")

                }
            }
        }

        binding.radioGroupTheme.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioButtonDark)
            {
                binding.radioButtonDark.background = ContextCompat.getDrawable(binding.radioButtonDark.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonLight.background = ContextCompat.getDrawable(binding.radioButtonLight.context, R.drawable.qr_radio_button_not_selected)
                if (Button.isDarkModeButtonClick==0){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    Button.isDarkModeButtonClick = 1
                }else{
                    Button.isDarkModeButtonClick = 0
                }


            }
            else if (checkedId == R.id.radioButtonLight) {
                if (Button.isLightModeButtonClick==0){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Button.isLightModeButtonClick = 1
                }else{
                    Button.isLightModeButtonClick = 0
                }
                binding.radioButtonLight.background = ContextCompat.getDrawable(binding.radioButtonLight.context, R.drawable.qr_radio_button_selected)
                binding.radioButtonDark.background = ContextCompat.getDrawable(binding.radioButtonDark.context, R.drawable.qr_radio_button_not_selected)

            }
        }

    }
    @SuppressLint("CommitPrefEdits")
    private fun rememberLanguage():String{
        val preferences = getSharedPreferences(Preferences.PREFS_FILENAME, Context.MODE_PRIVATE)
        val language = preferences.getString("Language", "Turkish")

        if(Preferences.isLanguageSet==0) {
            if (language != null) {
                if (language == "Turkish") {
                    updateLocale(Locales.Turkish)
                } else {
                    updateLocale(Locale.ENGLISH)
                }
            }
            Preferences.isLanguageSet=1
        }
             return language!!

    }
    private fun saveLanguage(language:String){
        val preferences = getSharedPreferences(Preferences.PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("Language",language)
        editor.apply()
    }
}