package com.example.intertech_account.view.main_page.fragment.account.adapter

sealed class AllAccountsRecyclerItem {


    class Title(
        val id: Int,
        val title: String
    ) : AllAccountsRecyclerItem()

    class Movie(
        val id: Int,
        val title: String,
        val thumbnail: String,
        val release_date: String
    ) : AllAccountsRecyclerItem()

    class Director(
        val id: Int,
        val name: String,
        val avatar: String,
        val movie_count: Int
    ) : AllAccountsRecyclerItem()

}