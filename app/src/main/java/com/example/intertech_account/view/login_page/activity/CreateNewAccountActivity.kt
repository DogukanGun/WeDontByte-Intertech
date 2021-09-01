package com.example.intertech_account.view.login_page.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intertech_account.databinding.ActivityCreateNewAccountBinding

class CreateNewAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNewAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateNewAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}