package com.example.intertech_account.view.main_page.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.intertech_account.BaseActivity
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityMainBinding
import com.example.intertech_account.model.api_model.GetCurrency
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view_model.GetCurrencyViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navHostFragment:NavHostFragment
    private val getCurrencyViewModel:GetCurrencyViewModel by viewModels()
    private var readToExit:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialization
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrencyViewModel.apiRequest()
        getCurrencyViewModel.getCurrencyModelResult.observe(this,{
            if (it.dataGet.getCurrencyList.isNotEmpty()){
                Constant.currencyList= it.dataGet.getCurrencyList.toCollection(ArrayList())
            }
        })

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
                    if (Button.isUserInformationTopBarButtonClick.value==0) {
                        Button.isUserInformationTopBarButtonClick.value = 1
                    }
                    true
                }

                // Topbar diller buttonu

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

    // geri buttonu
    override fun onBackPressed() {
        super.onBackPressed()
        if (readToExit==0){

        }
        readToExit-=1
        Toast.makeText(this,R.string.exit_message,Toast.LENGTH_LONG).show()
    }


}