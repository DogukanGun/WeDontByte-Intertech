package com.example.intertech_account.view.login_page.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityUserLoginBinding
import com.example.intertech_account.view_model.GetUserLoginViewModel

class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUserLoginBinding
    private val getUserLoginViewModel:GetUserLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_user_login)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.getUserLoginViewModel=getUserLoginViewModel

    }
}