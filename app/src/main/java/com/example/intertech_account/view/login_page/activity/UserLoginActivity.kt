package com.example.intertech_account.view.login_page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityUserLoginBinding
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view_model.GetUserLoginViewModel
import com.example.intertech_account.view_model.repo.CitizenshipControl

class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUserLoginBinding
    private val getUserLoginViewModel:GetUserLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        //Login page Backend-Frontend bağlanması
        getUserLoginViewModel.context=this
        getUserLoginViewModel.start()
        binding= ActivityUserLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginPagePassword.error=null
        buttonListen()

    }



    private fun buttonListen(){
        binding.addNewAccount.setOnClickListener {
            val intent = Intent(this,CreateNewAccountActivity::class.java)
            startActivity(intent)
        }
        //Login page button aksiyonu

        binding.loginPageLoginButton.setOnClickListener {
            controlUserLogin()
        }

        binding.forgetPasswordTextView.setOnClickListener {
            val action = Intent(this,ForgetPasswordActivity::class.java)
            startActivity(action)
        }

        binding.loginPageHelpMe.setOnClickListener {
            val intent = Intent(this,HelpScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun controlUserLogin() {
        if (binding.loginPagePasswordTextField.text.toString() == "123") {
            if (binding.loginPageUsernameTextField.text.toString().length != 11 || binding.loginPageUsernameTextField.text.toString()[0] == '0') {
                Toast.makeText(this, "Kimlik numarası geçersiz", Toast.LENGTH_LONG).show()
            } else {
                val citizennum = binding.loginPageUsernameTextField.text.toString()
                val citizenshipControl = CitizenshipControl()

                if (citizenshipControl.controlCitizenship(citizennum)) {
                    Toast.makeText(
                        this,
                        "Kimlik Numarası ve Şifre Doğru Giriş yapılıyor...",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Kimlik numarası geçersiz", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            getUserLoginViewModel.getUsers().observe(this,{
                if (it.isNotEmpty()){
                    for (index in it){
                        if (index.citizenshipID==binding.loginPageUsernameTextField.text.toString()&&index.password==binding.loginPagePasswordTextField.text.toString()){
                            val action= Intent(this, MainActivity::class.java)
                            startActivity(action)
                        }
                    }

                }
            })


        }
    }

}