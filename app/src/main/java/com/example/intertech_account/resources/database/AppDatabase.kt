package com.example.intertech_account.resources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.intertech_account.model.api_model.login_page.user.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
