package com.example.intertech_account.resources.common_variables.fragment

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
        "Ad" to 0,
        "Soyad" to 1,
        "Müşteri Numarası" to 2,
        "Hesap Adı" to 3,
        "Şube Adı" to 4,
        "Bakiye" to 5,
        "TRY cinsinden bakiye" to 6,
        "IBAN" to 7,
        "Döviz Kodu" to 8,
        "Hesap Türü" to 9,
        "Faiz Oranı" to 10,
        "Hesap açılış Tarihi" to 11,
        "Hesap Kapanış tarihi" to 12,
        "Hesap Bloke mi?" to 13,
        "Hesap Kapalı mı?" to 14,


        )
    val currencySigns: HashMap<String, String> = hashMapOf(
        "TRY" to "₺",
        "USD" to "$",
        "EUR" to "€",
        "GBP" to "£",
        "CAD" to "$"
    )
}