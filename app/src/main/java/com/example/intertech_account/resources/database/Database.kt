package com.example.intertech_account.resources.database

import android.content.Context
import androidx.room.Room

class Database (
            var context:Context
        )
{
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()
    private val userDao = db.userDao()

    fun getDao():UserDao{
       return userDao
    }


}