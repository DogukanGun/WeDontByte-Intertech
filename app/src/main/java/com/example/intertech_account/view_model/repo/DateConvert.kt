package com.example.intertech_account.view_model.repo

import android.content.Context
import androidx.core.content.contentValuesOf
import com.example.intertech_account.R

class DateConvert(var context: Context) {

    fun convertDate(date:String):String{
        when(date){
            "1","01","Ocak"->{
                return context.resources.getString(R.string.Jan)
            }
            "2","02","Şubat"->{
                return context.resources.getString(R.string.Feb)
            }
            "3","03","Mart"->{
                return context.resources.getString(R.string.Mar)
            }
            "4","04","Nisan"->{
                return context.resources.getString(R.string.Apr)
            }
            "5","05","Mayıs"->{
                return context.resources.getString(R.string.May)
            }
            "6","06","Haziran"->{
                return context.resources.getString(R.string.Jun)
            }
            "7","07","Temmuz"->{
                return context.resources.getString(R.string.Jul)
            }
            "8","08","Ağustos"->{
                return context.resources.getString(R.string.Aug)
            }
            "9","09","Eylül"->{
                return context.resources.getString(R.string.Sep)
            }
            "10","Ekim"->{
                return context.resources.getString(R.string.Oct)
            }
            "11","Kasım"->{
                return context.resources.getString(R.string.Nov)
            }
            "12","Aralık"->{
                return context.resources.getString(R.string.Dec)
            }
            else->{
                return "Unknown"
            }
        }
    }
}