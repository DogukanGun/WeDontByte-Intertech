package com.example.intertech_account.view.main_page.fragment.account

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.FragmentAllAccountsBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view.main_page.fragment.account.adapter.Swipe
import com.example.intertech_account.view.main_page.fragment.account.adapter.SwipeButton
import com.example.intertech_account.view.main_page.fragment.account.adapter.SwipeButtonClickListener
import com.example.intertech_account.view.main_page.fragment.main_page.MainPageFragmentDirections
import com.example.intertech_account.view_model.GetAccountViewModel


class AllAccountsFragment : Fragment() {

    private lateinit var binding:FragmentAllAccountsBinding
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    private lateinit var getAccountModel: GetAccountModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAccountsBinding.inflate(layoutInflater)
        var adapter=AllAccountsAdapter(arrayListOf())

        binding.allAccounts.adapter=adapter
        binding.allAccounts.layoutManager=LinearLayoutManager(activity)

        getAccountViewModel.apiRequest()
        getAccountViewModel.getAccountList.observe(viewLifecycleOwner,{
                getAccountModel=it
                var adapter_=binding.allAccounts.adapter as? AllAccountsAdapter
                adapter_!!.addAccount(getAccountModel.getAccountData.getAccountList)

        })
        val swipe=object: Swipe(adapter,requireContext(),binding.allAccounts,200){
            override fun instantiateSwipeButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<SwipeButton>
            ) {



                buffer.add(
                    SwipeButton(requireActivity(),
                    "Deneme",
                    30,
                    0,
                    Color.parseColor("#FF3C30"),
                    object: SwipeButtonClickListener {
                        override fun onClick(pos: Int) {
                            Log.d("Info :","Deneme basıldı")
                            print("asdasd")
                            Toast.makeText(requireActivity(),"Delete ID"+pos, Toast.LENGTH_SHORT).show()
                        }

                    })
                )

                buffer.add(
                    SwipeButton(requireActivity(),
                    "Update",
                    30,
                    0,
                    Color.parseColor("#FF9502"),
                    object: SwipeButtonClickListener {
                        override fun onClick(pos: Int) {
                            Toast.makeText(requireActivity(),"Delete ID"+pos, Toast.LENGTH_SHORT).show()
                        }

                    })
                )


            }
        }
        
        return binding.root

    }
    fun listenTopBarButton(){
            val action = MainPageFragmentDirections.actionMainPageFragmentToUserInformationFragment()
            Constant.navHostFragment.findNavController().navigate(action)
    }


}