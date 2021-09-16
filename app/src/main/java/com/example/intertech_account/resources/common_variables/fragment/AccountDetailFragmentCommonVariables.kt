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

    val currencySigns: HashMap<String, String> = hashMapOf(
        "TRY" to "₺",
        "USD" to "$",
        "EUR" to "€",
        "GBP" to "£",
        "CAD" to "$"
    )
}