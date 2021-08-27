package com.example.intertech_account.view_model.repo

class EncryptWord(var userPassword:String) {

    //TODO:Gaye kodlarini bu kisma yaz ben yapiyi olusturdum

    private lateinit var encryptedData:String

    fun getEncryptedData():String{
        encrypt()
        return encryptedData
    }
    private fun encrypt(){
        //user password burada kullanilabilir
        //this.userPassword -> boyle erisim

    }
}


// toas message fragment da nasil gosterilir
// toolbar button nasil kullanilir
//sagdan swipe icin hazir kutuphane var mi