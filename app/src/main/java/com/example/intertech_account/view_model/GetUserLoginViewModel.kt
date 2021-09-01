package com.example.intertech_account.view_model

 import androidx.lifecycle.ViewModel
 import com.example.intertech_account.model.api_model.login_page.user.User
 import com.example.intertech_account.model.api_model.login_page.user.UserOperationState
 import com.example.intertech_account.view_model.repo.CitizenshipControl

class GetUserLoginViewModel:ViewModel() {
    private val citizenshipControl=CitizenshipControl()
    fun check(user:User):UserOperationState{
        if (!citizenshipControl.controlCitizenship(user.citizenshipID)){
            return UserOperationState.WRONG_CITIZENSHIP_ID
        }
        if (user.password!=user.repeatPassword){
            return UserOperationState.PASSWORD_DOES_NOT_MATCH
        }
        if (user.password.length<=8){
            return UserOperationState.PASSWORD_LENGTH_MUST_BE_BIGGER_THAN_8_CHARACTERS
        }
        if (user.nameAndSurname.split(" ").size<2){
            return UserOperationState.MISSING_NAME_OR_SURNAME
        }
        return UserOperationState.NO_ERROR
    }
    fun printUserOperationError(state:UserOperationState):String{
        when(state){
            UserOperationState.NO_ERROR->{

            }
            UserOperationState.MISSING_NAME_OR_SURNAME->{

            }
            UserOperationState.MISSING_OR_EMPTY_LABEL->{

            }
            UserOperationState.PASSWORD_DOES_NOT_MATCH->{

            }
            UserOperationState.PASSWORD_LENGTH_MUST_BE_BIGGER_THAN_8_CHARACTERS->{

            }
            UserOperationState.WRONG_CITIZENSHIP_ID->{

            }
            UserOperationState.WRONG_PASSWORD->{

            }
            else -> {

            }
        }
        return ""
    }
}