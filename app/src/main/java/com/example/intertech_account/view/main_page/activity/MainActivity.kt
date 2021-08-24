package com.example.intertech_account.view.main_page.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityMainBinding
import com.example.intertech_account.view_model.GetAccountViewModel
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val getAccountViewModel:GetAccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.navController)

    }


}