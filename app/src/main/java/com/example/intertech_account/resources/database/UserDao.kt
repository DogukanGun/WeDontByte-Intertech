package com.example.intertech_account.resources.database

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.intertech_account.model.api_model.login_page.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Insert
    suspend fun registerUser(user:User)

    @Query("UPDATE user SET password=:password WHERE citizenship_ID=:citizenship_ID")
    suspend fun updateUser(password:String,citizenship_ID:String)

}