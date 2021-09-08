package com.example.intertech_account.view.main_page.fragment.account

import android.accounts.Account
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_account.databinding.FragmentAccountDetailBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.main_page.landmark.Landmark
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.fragment.account.adapter.AccountDetailAdapter
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view_model.GetAccountViewModel

class AccountDetailFragment(adapter: AllAccountsAdapter) : Fragment() {


    private lateinit var binding:FragmentAccountDetailBinding
    private var adapter = AccountDetailAdapter()
    private var adapter_ = AccountDetailAdapter()
    private lateinit var getAccountModel: GetAccountModel
    val args: AccountDetailFragmentArgs by navArgs()
    private val getAccountViewModel:GetAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAccountDetailBinding.inflate(layoutInflater)
        val view = binding.root

        /*val ad = Landmark ( "Ad",kome = "Onur")
        val soyad= Landmark(name="Soyad",kome = "Ağdoğan")
        val musno = Landmark(name="Müşteri Numarası",kome="45464859")
        val hesno= Landmark(name="Hesap Numarası",kome="5857-54561418-548")
        val sube= Landmark(name="Şube",kome="Tem")
        val iban= Landmark(name="IBAN",kome="TR49-1415-1647-2529-3536-4785-14")
        val hestar= Landmark(name="Hesap Açılış Tarihi",kome="14.04.2020")
        val hestur= Landmark(name="Hesap Türü",kome="Vadeli")
        val dov= Landmark(name="Döviz Kodu",kome="949")
        val bakıye= Landmark(name="Bakiye",kome="588 TL")
        val kulbak= Landmark(name="Kullanılabilir Bakiye",kome="588 TL")
        val sonhar= Landmark(name="Son Hareket Tarihi",kome="28.08.2021")



        landmarkList.add(ad)
        landmarkList.add(soyad)
        landmarkList.add(musno)
        landmarkList.add(hesno)
        landmarkList.add(sube)
        landmarkList.add(iban)
        landmarkList.add(hestar)
        landmarkList.add(hestur)
        landmarkList.add(dov)
        landmarkList.add(bakıye)
        landmarkList.add(kulbak)
        landmarkList.add(sonhar)

         */


        /*binding.button1.setOnClickListener{

        }*/
        var titles = arrayListOf<String>()
        var values = arrayListOf<String>()

        adapter = AccountDetailAdapter()
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager=LinearLayoutManager(activity)
        Log.d("Info",args.accountDetailFragmentListValues.toString())


        if(!args.accountDetailFragmentListValues.isNullOrEmpty()){
            values.addAll(args.accountDetailFragmentListValues!!.split("?"))

            for(i in values){
                titles.add(i.toString())
            }



        var arrList = ArrayList<GetAccountList>()
        arrList.addAll(getAccountModel.getAccountData.getAccountList.toCollection(ArrayList()))

        adapter_= (binding.recyclerview.adapter as? AccountDetailAdapter)!!
        adapter_.addAccount(titles,values)

    }

        return binding.root


    }


}
