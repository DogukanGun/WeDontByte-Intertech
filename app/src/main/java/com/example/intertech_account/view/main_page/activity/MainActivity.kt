package com.example.intertech_account.view.main_page.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.intertech_account.BaseActivity
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityMainBinding
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.resources.common_variables.Preferences
import com.example.intertech_account.view_model.GetCurrencyViewModel
import com.zeugmasolutions.localehelper.Locales
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navHostFragment:NavHostFragment
    private val getCurrencyViewModel:GetCurrencyViewModel by viewModels()
    private var readToExit:Int=1

    override fun updateLocale(locale: Locale) {
        super.updateLocale(locale)
        setTitle(R.string.app_title)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialization
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        getCurrency()
        rememberLanguage()
        setUpNavigation()
        toolBarListen()
        toolBarMenuButtonListen()





    }

    private fun getCurrency(){
        getCurrencyViewModel.apiRequest()
        getCurrencyViewModel.getCurrencyModelResult.observe(this,{
            if (it.dataGet.getCurrencyList.isNotEmpty()){
                Constant.currencyList= it.dataGet.getCurrencyList.toCollection(ArrayList())
            }
        })
    }
    private fun setUpNavigation(){
        navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        Constant.navHostFragment=navHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.navController)

        //TODO doldurulacak

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.mainPageFragment,
            R.id.allAccountsFragment,
        ).build()
        Button.qrButtonPressed.observe(this,{
            if (it.intentToCamera){
                val intent = Intent(this, QrReadWithCameraActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        binding.toolbar.setupWithNavController(Constant.navHostFragment.navController, appBarConfiguration)

    }

    private fun toolBarMenuButtonListen(){
        //Topbar button yönlendirmesi.
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            var id = menuItem.itemId
            when (menuItem.itemId) {
                // Topbar Kullanıcı bilgileri buttonu

                R.id.userInformationTopBarButton -> {
//                     findNavController().navigate(R.id.action_allAccountsFragment_to_simpleAccountFragment)
//                    if(Constant.isUserInformationTopBarButtonClick.value==0){
//                        Constant.isUserInformationTopBarButtonClick.value=1
//                    }
                    if(Constant.currentBottomMenu==0){
                        if (Button.isUserInformationTopBarButtonClickFromMainPageFragment.value==0) {
                            Button.isUserInformationTopBarButtonClickFromMainPageFragment.value = 1
                        }
                    }else{
                        if (Button.isUserInformationTopBarButtonClickFromAllAccounts.value==0) {
                            Button.isUserInformationTopBarButtonClickFromAllAccounts.value = 1
                        }
                    }

                    true
                }
                R.id.settingTopBarButton->{
                    if(Constant.currentBottomMenu==0){
                        if (Button.isSettingTopBarButtonClickFromMainPageFragment.value==0) {
                            Button.isSettingTopBarButtonClickFromMainPageFragment.value = 1
                        }
                    }else{
                        if (Button.isSettingTopBarButtonClickFromAllAccountsFragment.value==0) {
                            Button.isSettingTopBarButtonClickFromAllAccountsFragment.value = 1
                        }
                    }
                    true

                }



                else -> {
                    false
                }
            }
        }
    }
    private fun toolBarListen(){
        Button.isEnglishLanguageButtonClick.observe(this,{
            if (it==1){
                updateLocale(Locales.English)
                saveLanguage("English")
                Button.isEnglishLanguageButtonClick.value=0
            }
        })
        Button.isTurkishLanguageButtonClick.observe(this,{
            if (it==1){
                updateLocale(Locales.Turkish)
                saveLanguage("Turkish")
                Button.isTurkishLanguageButtonClick.value=0
            }
        })
        Button.isLightModeButtonClick.observe(this,{
            if (it==1){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
        })
        Button.isDarkModeButtonClick.observe(this,{
            if (it==1){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
        })
    }


    @SuppressLint("CommitPrefEdits")
    private fun rememberLanguage(){
        if (Preferences.isLanguageSet==0){
            val preferences = getSharedPreferences(Preferences.PREFS_FILENAME, Context.MODE_PRIVATE)
            val language=preferences.getString("Language","Turkish")
            if (language != null){
                if (language == "Turkish"){
                    updateLocale(Locales.Turkish)
                }else{
                    updateLocale(Locale.ENGLISH)
                }
            }
            Preferences.isLanguageSet=1
        }

    }
    private fun saveLanguage(language:String){
        val preferences = getSharedPreferences(Preferences.PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("Language",language)
        editor.apply()
    }




}