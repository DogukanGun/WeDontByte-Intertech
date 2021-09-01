package com.example.intertech_account.view_model.repo

import android.content.Intent
import android.widget.Toast
import com.example.intertech_account.view.main_page.activity.MainActivity

class CitizenshipControl {

    fun controlCitizenship(citizennum:String):Boolean{
        var cit10: Int = citizennum[0].toInt()-48
        cit10+=citizennum[2].toInt()-48
        cit10+=citizennum[4].toInt()-48
        cit10+=citizennum[6].toInt()-48
        cit10+=citizennum[8].toInt()-48
        cit10 *= 7
        cit10 -= (citizennum[1].toInt()-48) + (citizennum[3].toInt()-48) + (citizennum[5].toInt()-48) + (citizennum[7].toInt()-48)
        cit10 %= 10
        var cit11: Int = cit10 + (citizennum[0].toInt()-48)
        cit11+=citizennum[1].toInt()-48
        cit11+=citizennum[2].toInt()-48
        cit11+=citizennum[3].toInt()-48
        cit11+=citizennum[4].toInt()-48
        cit11+=citizennum[5].toInt()-48
        cit11+=citizennum[6].toInt()-48
        cit11+=citizennum[7].toInt()-48
        cit11+=citizennum[8].toInt()-48
        cit11 %= 10
        return cit10 == (citizennum[9].toInt()-48) && cit11 == (citizennum[10].toInt()-48)
    }
}