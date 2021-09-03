package com.example.intertech_account.view.login_page.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.intertech_account.databinding.ActivityForgetPasswordBinding
import com.example.intertech_account.model.api_model.login_page.user.User
import com.example.intertech_account.model.api_model.status.UserOperationState
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view_model.GetUserLoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var binding:ActivityForgetPasswordBinding
    private val getUserLoginViewModel:GetUserLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resetPasswordButton.setOnClickListener {
            val checkLabels=checkLabels()
            if (checkLabels == UserOperationState.NO_ERROR){
                Toast.makeText(this,"Basarili sifreniz yenilendi", Toast.LENGTH_LONG).show()
                 getUserLoginViewModel.getUsers().observe(this,{
                    if (it.isNotEmpty()){
                        for (index in it){
                            if (index.citizenshipID==binding.forgetPasswordCitizenshipIDTextField.text.toString()){
                                CoroutineScope(Dispatchers.IO).launch {
                                    getUserLoginViewModel.updateUser(binding.forgetPasswordPasswordTextField.text.toString(),binding.forgetPasswordCitizenshipIDTextField.text.toString())
                                }

                                val action= Intent(this, MainActivity::class.java)
                                startActivity(action)
                            }
                        }

                    }
                })


            }else{
                Toast.makeText(this,getUserLoginViewModel.printUserOperationError(checkLabels),
                    Toast.LENGTH_LONG).show()
            }
        }

        binding.forgetPasswordBackButton.setOnClickListener{
            val intent=Intent(this,UserLoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkLabels(): UserOperationState {
        if (binding.forgetPasswordCitizenshipIDTextField.text.isNullOrBlank()||
            binding.forgetPasswordPasswordTextField.text.isNullOrBlank()||
            binding.forgetPasswordRepeatPasswordTextField.text.isNullOrBlank()){
            return UserOperationState.MISSING_OR_EMPTY_LABEL
        }
        val user = User(UUID.randomUUID().toString(),
            "",
            binding.forgetPasswordCitizenshipIDTextField.text.toString(),
            binding.forgetPasswordPasswordTextField.text.toString(),
            binding.forgetPasswordRepeatPasswordTextField.text.toString())
        return getUserLoginViewModel.check(user)

    }
}