package com.example.intertech_account.view.main_page.fragment.qr_operation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentQRCodeOptionSelectBinding
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.resources.common_variables.QrOperation
import com.example.intertech_account.view.login_page.activity.UserLoginActivity
import com.example.intertech_account.view_model.QrCodeGenerateViewModel

class QRCodeOptionSelectFragment : Fragment() {

    private lateinit var binding:FragmentQRCodeOptionSelectBinding
    private val qrCodeGenerateViewModel:QrCodeGenerateViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQRCodeOptionSelectBinding.inflate(layoutInflater)
        arguments?.getString("iban").let {
            if (it != null) {
                if(it.isNotEmpty()){
                    binding.textView4.text = it
                }
            }
        }
        if (binding.depositMoney.isActivated){
            binding.button.setOnClickListener {
                if(checkAllParametersBeforeGenerateQrCode()){
                    qrCodeGenerateViewModel.addParameter(binding.destinationIban.toString())
                    qrCodeGenerateViewModel.addParameter(binding.amount.text.toString())
                    var qr=qrCodeGenerateViewModel.getQrCode()
                    val design = layoutInflater.inflate(R.layout.qr_code_alert_dialog,null)
                    var image = design.findViewById<ImageView>(R.id.qr_code_alert_dialog)
                    if (qr!=null){
                        image.setImageBitmap(qr)
                    }else{
                        //TODO buraya ucgen hata resmi konulabilir
                    }
                    val ad = AlertDialog.Builder(requireContext())
                    ad.setTitle("SCAN QR")
                    ad.setView(design)
                    ad.setPositiveButton(R.string.okey_button){ dialogInterface,i->

                        sendRequestToAction()
                    }
                    ad.setNegativeButton(R.string.cancel_button){dialogInterface,i->
                        dialogInterface.cancel()
                    }
                    ad.create().show()


                }


            }
            if (binding.drawMoney.isActivated){
                com.example.intertech_account.resources.common_variables.Button.qrButtonPressed.value=QrOperation(false,"",true)
            }
        }

        return binding.root
    }
    private fun sendRequestToAction(){
        var action =QRCodeOptionSelectFragmentDirections.actionQRCodeOptionSelectFragmentToMainPageFragment()
        Constant.navHostFragment.navController.navigate(action)
    }
    private fun checkAllParametersBeforeGenerateQrCode():Boolean{
        if (binding.destinationIban.text.toString().isEmpty()){
            return false
        }
        if (binding.amount.text.toString().isEmpty()){
            return false
        }
        return true


    }
}