package com.example.intertech_account.resources.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.intertech_account.model.api_model.login_page.user.User
import com.example.intertech_account.resources.database.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAll()

    suspend fun insert(user: User) {
        userDao.registerUser(user)
    }
    suspend fun update(password:String,citizenship_ID:String){
        userDao.updateUser(password,citizenship_ID)
    }

}