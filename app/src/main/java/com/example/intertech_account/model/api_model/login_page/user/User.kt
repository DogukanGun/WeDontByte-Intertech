package com.example.intertech_account.model.api_model.login_page.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity

data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "name_and_surname")var nameAndSurname:String,
    @ColumnInfo(name = "citizenship_ID")var citizenshipID:String,
    @ColumnInfo(name = "password")var password:String,
    var repeatPassword:String,
)
{
}