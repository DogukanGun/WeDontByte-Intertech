package com.example.intertech_account.view.login_page.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.intertech_account.databinding.ActivityUserLoginBinding
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view_model.GetUserLoginViewModel

class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUserLoginBinding
    private val getUserLoginViewModel:GetUserLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        //Login page Backend-Frontend bağlanması

        binding= ActivityUserLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginPagePassword.error=null


        //Login page button aksiyonu
        // TODO buradaki login bilgileri değiştirilecek

        binding.loginPageLoginButton.setOnClickListener {
            if (binding.loginPagePasswordTextField.text.toString()=="deneme"){
            }
            if(binding.loginPageUsernameTextField.text.toString()=="deneme") {

            }
                Toast.makeText(this,"Doğru şifre. Giriş yapılıyor...",Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

        }

        binding.loginPageHelpMe.setOnClickListener {
            // TODO burada web view ile denizbanka yonlendir
        }

    }
}