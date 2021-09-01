package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAccountDetailBinding
import com.example.intertech_account.view.main_page.fragment.account.adapter.AccountDetailAdapter

class AccountDetailFragment : Fragment() {


    private lateinit var binding:FragmentAccountDetailBinding
    private lateinit var landmarkList:ArrayList<Landmark>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAccountDetailBinding.inflate(layoutInflater)
        val view = binding.root


        landmarkList = ArrayList<Landmark>()

        val ad = Landmark ( "Ad",kome = "Onur")
        val soyad= Landmark(name="Soyad",kome = "Ağdoğan")
        val musno = Landmark(name="Müşteri Numarası",kome="45464859")
        val hesno= Landmark(name="Hesap Numarası",kome="5857-54561418-548")
        val sube=Landmark(name="Şube",kome="Tem")
        val iban=Landmark(name="IBAN",kome="TR49-1415-1647-2529-3536-4785-14")
        val hestar=Landmark(name="Hesap Açılış Tarihi",kome="14.04.2020")
        val hestur=Landmark(name="Hesap Türü",kome="Vadeli")
        val dov=Landmark(name="Döviz Kodu",kome="949")
        val bakıye=Landmark(name="Bakiye",kome="588 TL")
        val kulbak=Landmark(name="Kullanılabilir Bakiye",kome="588 TL")
        val sonhar=Landmark(name="Son Hareket Tarihi",kome="28.08.2021")



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






        binding.recyclerview.layoutManager= LinearLayoutManager(activity)
        val LandmarkAdapter = AccountDetailAdapter(landmarkList)
        binding.recyclerview.adapter = LandmarkAdapter


        return binding.root


    }

}