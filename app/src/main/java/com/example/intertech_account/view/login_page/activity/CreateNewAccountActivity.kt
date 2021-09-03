package com.example.intertech_account.view.login_page.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityCreateNewAccountBinding
import com.example.intertech_account.model.api_model.login_page.user.User
import com.example.intertech_account.model.api_model.status.UserOperationState
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view_model.GetUserLoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CreateNewAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNewAccountBinding
    private val getUserLoginViewModel:GetUserLoginViewModel by viewModels()
    private lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserLoginViewModel.context=this
        getUserLoginViewModel.start()
        binding= ActivityCreateNewAccountBinding.inflate(layoutInflater)
        binding.createAccountSaveButton.setOnClickListener{
            val checkLabels=checkLabels()
            if (checkLabels == UserOperationState.NO_ERROR){
                Toast.makeText(this,getString(R.string.successfully_registered),Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.IO).launch {
                    getUserLoginViewModel.insertUser(user)
                }
                Toast.makeText(this,"Basarili kayit oldunuz",Toast.LENGTH_LONG).show()
                val action=Intent(this,MainActivity::class.java)
                startActivity(action)
            }else{
                Toast.makeText(this,getUserLoginViewModel.printUserOperationError(checkLabels),Toast.LENGTH_LONG).show()
            }
        }
        setContentView(binding.root)

        binding.createAccountBackButton.setOnClickListener{
            val intent=Intent(this,UserLoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkLabels(): UserOperationState {
        if (binding.createAccountUsernameTextField.text.isNullOrBlank()||
            binding.createAccountCitizenshipIDTextField.text.isNullOrBlank()||
            binding.createAccountPasswordTextField.text.isNullOrBlank()||
            binding.createAccountRepeatPasswordTextField.text.isNullOrBlank()){
            return UserOperationState.MISSING_OR_EMPTY_LABEL
        }
        user = User(
                        UUID.randomUUID().toString(),
                        binding.createAccountUsernameTextField.text.toString(),
                        binding.createAccountCitizenshipIDTextField.text.toString(),
                        binding.createAccountPasswordTextField.text.toString(),
                        binding.createAccountRepeatPasswordTextField.text.toString())
        return getUserLoginViewModel.check(user)

    }
}