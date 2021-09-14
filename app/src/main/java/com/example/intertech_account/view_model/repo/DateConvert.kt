package com.example.intertech_account.view_model.repo

import android.content.Context
import androidx.core.content.contentValuesOf
import com.example.intertech_account.R

class DateConvert(var context: Context) {

    fun convertDate(date:String):String{
        when(date){
            "1","01"->{
                return context.resources.getString(R.string.Jan)
            }
            "2","02"->{
                return context.resources.getString(R.string.Feb)
            }
            "3","03"->{
                return context.resources.getString(R.string.Mar)
            }
            "4","04"->{
                return context.resources.getString(R.string.Apr)
            }
            "5","05"->{
                return context.resources.getString(R.string.May)
            }
            "6","06"->{
                return context.resources.getString(R.string.Jun)
            }
            "7","07"->{
                return context.resources.getString(R.string.Jul)
            }
            "8","08"->{
                return context.resources.getString(R.string.Aug)
            }
            "9","09"->{
                return context.resources.getString(R.string.Sep)
            }
            "10"->{
                return context.resources.getString(R.string.Oct)
            }
            "11"->{
                return context.resources.getString(R.string.Nov)
            }
            "12"->{
                return context.resources.getString(R.string.Dec)
            }
            else->{
                return "Unknown"
            }
        }
    }
}