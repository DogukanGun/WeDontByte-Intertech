package com.example.intertech_account.view.main_page.activity

import android.graphics.Path
import android.os.Bundle
import android.text.Layout
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityMainBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.fragment.main_page.MainPageFragmentDirections
import com.example.intertech_account.view_model.GetAccountViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navHostFragment:NavHostFragment
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

        binding.toolbar.setupWithNavController(Constant.navHostFragment.navController, appBarConfiguration)
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.userInformationTopBarButton -> {

                    Constant.isUserInformationTopBarButtonClick.value=true

                    true
                }
                else -> {
                   false
                }
            }
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this,R.string.exit_message,Toast.LENGTH_LONG).show()
    }


}