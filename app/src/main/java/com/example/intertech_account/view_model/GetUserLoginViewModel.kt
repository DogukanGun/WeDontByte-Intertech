package com.example.intertech_account.view_model

 import android.content.Context
 import androidx.lifecycle.*
 import com.example.intertech_account.R
 import com.example.intertech_account.model.api_model.login_page.user.User
 import com.example.intertech_account.model.api_model.status.UserOperationState
 import com.example.intertech_account.resources.database.Database
 import com.example.intertech_account.resources.database.UserDao
 import com.example.intertech_account.resources.database.repository.UserRepository
 import com.example.intertech_account.view_model.repo.CitizenshipControl

class GetUserLoginViewModel():ViewModel() {
    lateinit var context: Context
    private val citizenshipControl=CitizenshipControl()
    private lateinit var userDao:UserDao
    private lateinit var userRepository:UserRepository
    private val _response = MutableLiveData<Boolean>()
    fun start(){
        userDao=Database(context).getDao()
        userRepository= UserRepository(userDao)

    }

    var user = MutableLiveData<User>()
    suspend fun insertUser(user: User){
         userRepository.insert(user)
    }
    suspend fun updateUser(password: String,citizenship_ID:String){
        userRepository.update(password,citizenship_ID)
    }
    fun getUsers(): LiveData<List<User>> {
        return userDao.getAll()
    }

    fun check(user:User): UserOperationState {
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
    fun printUserOperationError(state: UserOperationState):String{
        when(state){
            UserOperationState.NO_ERROR->{
                return R.string.no_error.toString()
            }
            UserOperationState.MISSING_NAME_OR_SURNAME->{
                return R.string.missing_namesurname.toString()
            }
            UserOperationState.MISSING_OR_EMPTY_LABEL->{
                return R.string.empty_label.toString()
            }
            UserOperationState.PASSWORD_DOES_NOT_MATCH->{
                return R.string.password_not_match.toString()
            }
            UserOperationState.PASSWORD_LENGTH_MUST_BE_BIGGER_THAN_8_CHARACTERS->{
                return R.string.password_short_length.toString()
            }
            UserOperationState.WRONG_CITIZENSHIP_ID->{
                return R.string.wrong_id_number.toString()
            }
            UserOperationState.WRONG_PASSWORD->{
                return R.string.wrong_password.toString()
            }
            else -> {

            }
        }
        return ""
    }
}