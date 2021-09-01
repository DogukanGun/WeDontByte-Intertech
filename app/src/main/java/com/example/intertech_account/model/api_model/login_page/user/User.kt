package com.example.intertech_account.model.api_model.login_page.user

data class User(
    var nameAndSurname:String,
    var citizenshipID:String,
    var password:String,
    var repeatPassword:String,
)
{
}