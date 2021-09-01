package com.example.intertech_account.view.login_page.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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


        binding.addNewAccount.setOnClickListener {
            val intent = Intent(this,CreateNewAccountActivity::class.java)
            startActivity(intent)
        }
        //Login page button aksiyonu

        binding.loginPageLoginButton.setOnClickListener {

            /*if(true){
                Toast.makeText(this,"Kimlik Numarası ve Şifre Doğru Giriş yapılıyor...",Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }*/

            if (binding.loginPagePasswordTextField.text.toString()=="123"){
                if (binding.loginPageUsernameTextField.text.toString().length!=11||binding.loginPageUsernameTextField.text.toString()[0]=='0'){
                    Toast.makeText(this,"Kimlik numarası geçersiz",Toast.LENGTH_LONG).show()
                }
                else {
                    var citizennum=binding.loginPageUsernameTextField.text.toString()
                    var cit10: Int = citizennum[0].toInt()-48
                    cit10+=citizennum[2].toInt()-48
                    cit10+=citizennum[4].toInt()-48
                    cit10+=citizennum[6].toInt()-48
                    cit10+=citizennum[8].toInt()-48
                    cit10 *= 7
                    cit10 -= (citizennum[1].toInt()-48) + (citizennum[3].toInt()-48) + (citizennum[5].toInt()-48) + (citizennum[7].toInt()-48)
                    cit10 %= 10
                    var cit11: Int = cit10 + (citizennum[0].toInt()-48)
                    cit11+=citizennum[1].toInt()-48
                    cit11+=citizennum[2].toInt()-48
                    cit11+=citizennum[3].toInt()-48
                    cit11+=citizennum[4].toInt()-48
                    cit11+=citizennum[5].toInt()-48
                    cit11+=citizennum[6].toInt()-48
                    cit11+=citizennum[7].toInt()-48
                    cit11+=citizennum[8].toInt()-48
                    cit11 %= 10
                    if (cit10 == (citizennum[9].toInt()-48) && cit11 == (citizennum[10].toInt()-48)) {
                        Toast.makeText(this,"Kimlik Numarası ve Şifre Doğru Giriş yapılıyor...",Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Kimlik numarası geçersiz",Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Şifre Hatalı",Toast.LENGTH_LONG).show()
            }




        }

        binding.loginPageHelpMe.setOnClickListener {
            val intent = Intent(this,HelpScreenActivity::class.java)
            startActivity(intent)
        }

    }
}