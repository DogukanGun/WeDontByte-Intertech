package com.example.intertech_account.view.login_page.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.intertech_account.databinding.ActivityCreateNewAccountBinding
import com.example.intertech_account.model.api_model.login_page.user.User
import com.example.intertech_account.model.api_model.login_page.user.UserOperationState
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view_model.GetUserLoginViewModel

class CreateNewAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNewAccountBinding
    private val getUserLoginViewModel:GetUserLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateNewAccountBinding.inflate(layoutInflater)
        binding.createAccountSaveButton.setOnClickListener{
            val checkLabels=checkLabels()
            if (checkLabels == UserOperationState.NO_ERROR){
                Toast.makeText(this,"Basarili kayit oldunuz",Toast.LENGTH_LONG).show()
                val action=Intent(this,MainActivity::class.java)
                startActivity(action)
            }else{
                Toast.makeText(this,getUserLoginViewModel.printUserOperationError(checkLabels),Toast.LENGTH_LONG).show()
            }
        }
        setContentView(binding.root)

        binding.createAccountBackButton.setOnClickListener{
                    this?.onBackPressed()
        }

    }

    private fun checkLabels():UserOperationState{
        if (binding.createAccountUsernameTextField.text.isNullOrBlank()||
            binding.createAccountCitizenshipIDTextField.text.isNullOrBlank()||
            binding.createAccountPasswordTextField.text.isNullOrBlank()||
            binding.createAccountRepeatPasswordTextField.text.isNullOrBlank()){
            return UserOperationState.MISSING_OR_EMPTY_LABEL
        }
        val user = User(binding.createAccountUsernameTextField.text.toString(),
                        binding.createAccountCitizenshipIDTextField.text.toString(),
                        binding.createAccountPasswordTextField.text.toString(),
                        binding.createAccountRepeatPasswordTextField.text.toString())
        return getUserLoginViewModel.check(user)

    }
}