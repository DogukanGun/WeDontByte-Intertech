package com.example.intertech_account.view_model.repo

class DateConvert {

    fun convertDate(date:String):String{
        when(date){
            "1","01"->{
                return "January"
            }
            "2","02"->{
                return "February"
            }
            "3","03"->{
                return "March"
            }
            "4","04"->{
                return "April"
            }
            "5","05"->{
                return "May"
            }
            "6","06"->{
                return "June"
            }
            "7","07"->{
                return "July"
            }
            "8","08"->{
                return "August"
            }
            "9","09"->{
                return "September"
            }
            "10"->{
                return "October"
            }
            "11"->{
                return "November"
            }
            "12"->{
                return "December"
            }
            else->{
                return "Unknown"
            }
        }
    }
}