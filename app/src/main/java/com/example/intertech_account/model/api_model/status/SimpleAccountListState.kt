package com.example.intertech_account.model.api_model.status

import android.widget.Button
import com.example.intertech_account.R

enum class SimpleAccountListState(var day:Int) {
    LAST_ONE_WEEK(7),
    LAST_ONE_MONTH(30),
    LAST_THREE_MONTHS(90),
    LAST_SIX_MONTHS(180),
    LAST_ONE_YEAR(365),
    DETAIL(0),
    NO_FILTER(0)
}