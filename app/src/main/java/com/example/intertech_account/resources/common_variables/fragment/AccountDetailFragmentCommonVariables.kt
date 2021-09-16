package com.example.intertech_account.resources.common_variables.fragment

import android.content.res.Resources
import com.example.intertech_account.R

object AccountDetailFragmentCommonVariables {
     val titlesHashMap = hashMapOf(
        "isBlocked" to "Hesap Bloke mi?",
        "branch" to "Şube Adı",
        "isClosed" to "Hesap Kapalı mı?",
        "currency" to "Döviz Kodu",
        "interestRate" to "Faiz Oranı",
        "balance" to "Bakiye",
        "accountName" to "Hesap Adı",
        "iban" to "IBAN",
        "balanceAsTRY" to "TRY cinsinden bakiye",
        "closingDate" to "Hesap Kapanış tarihi",
        "openingDate" to "Hesap açılış Tarihi",
        "customerNo" to "Müşteri Numarası",
    )
    val roles = hashMapOf(

        Resources.getSystem().getString(
            R.string.name) to 0,
        Resources.getSystem().getString(
            R.string.surname) to 1,
        Resources.getSystem().getString(
            R.string.account_number) to 2,
        Resources.getSystem().getString(
            R.string.account_name) to 3,
        Resources.getSystem().getString(
            R.string.branch_name) to 4,
        Resources.getSystem().getString(
            R.string.amaount) to 5,
        Resources.getSystem().getString(
            R.string.amount_try) to 6,
        Resources.getSystem().getString(
            R.string.iban) to 7,
        Resources.getSystem().getString(
            R.string.currency) to 8,
        Resources.getSystem().getString(
            R.string.account_type) to 9,
        Resources.getSystem().getString(
            R.string.interest) to 10,
        Resources.getSystem().getString(
            R.string.start_date) to 11,
        Resources.getSystem().getString(
            R.string.end_date)  to 12,
        Resources.getSystem().getString(
            R.string.block)  to 13,
        Resources.getSystem().getString(
            R.string.deleted)  to 14,


        )
    val currencySigns: HashMap<String, String> = hashMapOf(
        "TRY" to "₺",
        "USD" to "$",
        "EUR" to "€",
        "GBP" to "£",
        "CAD" to "$"
    )
}