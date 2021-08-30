package com.example.intertech_account.view.main_page.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.intertech_account.BaseActivity
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityMainBinding
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.login_page.activity.UserLoginActivity
import org.intellij.lang.annotations.Language
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navHostFragment:NavHostFragment
    private var readToExit:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        Constant.navHostFragment=navHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.navController)

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
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            var id = menuItem.itemId
            when (menuItem.itemId) {
                R.id.userInformationTopBarButton -> {
//                     findNavController().navigate(R.id.action_allAccountsFragment_to_simpleAccountFragment)
//                    if(Constant.isUserInformationTopBarButtonClick.value==0){
//                        Constant.isUserInformationTopBarButtonClick.value=1
//                    }
                    if (Button.isUserInformationTopBarButtonClick.value==0) {
                        Button.isUserInformationTopBarButtonClick.value = 1
                    }
                    true
                }
                R.id.languageTopBarButton->{
                    val sharedPreferences =getSharedPreferences("SettingsActivity", MODE_PRIVATE)
                    val language = sharedPreferences.getString("language", "English")
                    if (language.equals("English")){
                        sharedPreferences.edit().putString("language","Turkish")
                        restartApp("Turkish")
                    }else{
                        sharedPreferences.edit().putString("language","English")
                        restartApp("English")
                    }

                    true
                }

                else -> {
                   false
                }
            }
        }

    }

    private fun restartApp(language: String){
        BaseActivity.dLocale = Locale(language)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (readToExit==0){

        }
        readToExit-=1
        Toast.makeText(this,R.string.exit_message,Toast.LENGTH_LONG).show()
    }


}