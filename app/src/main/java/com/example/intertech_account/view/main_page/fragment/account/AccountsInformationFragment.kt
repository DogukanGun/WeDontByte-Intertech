package com.example.intertech_account.view.main_page.fragment.account

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.*
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAccountsInformationBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.resources.common_variables.QrOperation
import com.example.intertech_account.view.main_page.fragment.main_page.MainPageFragmentDirections
import com.example.intertech_account.view_model.GetAccountViewModel
import android.content.Context.CLIPBOARD_SERVICE as CLIPBOARD_SERVICE1


class AccountsInformationFragment : Fragment() {
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    lateinit var getAccountModel: GetAccountModel
    private lateinit var currentIban: String
    var isFragmentUsedByViewPager = false
    lateinit var binding: FragmentAccountsInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountsInformationBinding.inflate(layoutInflater)
        if (isFragmentUsedByViewPager) {
            updateLabel(0)
            val spinnerList: ArrayList<String> = arrayListOf<String>()

            //Bütün hesapların eklenmesi
            for (index in getAccountModel.getAccountData.getAccountList) {
                spinnerList.add(index.accountName + " / " + index.balance + " " + index.currency)
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                spinnerList
            )
            binding.accountName.adapter = adapter
            binding.qrButton.setOnClickListener {
                Button.qrButtonPressed.value = QrOperation(true, currentIban, false)
            }

            binding.accountType.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(Intent.EXTRA_TEXT, binding.accountType.text)
                intent.type = "text/plain"
                startActivity(intent)
            }
            isFragmentUsedByViewPager = false
            binding.accountName.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long
                    ) {
                        updateLabel(position)


                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // your code here
                    }
                }
        }

        executeSharePopupMenu(inflater)
        return binding.root
    }

    //Hesap sayfasının bilgi güncellemesi

    fun updateLabel(position: Int) {
        binding.subeText.text = getAccountModel.getAccountData.getAccountList[position].branch
        binding.accountType.text = getAccountModel.getAccountData.getAccountList[position].iban
        binding.accountBalance.text =
            (getAccountModel.getAccountData.getAccountList[position].availableBalance + 1500.0).toString() + " " + getAccountModel.getAccountData.getAccountList[position].currency.toString()
        currentIban = getAccountModel.getAccountData.getAccountList[position].iban
        if (getAccountModel.getAccountData.getAccountList[position].interestRate == 0.0) {
            binding.vadeliText.text =
                "Vadesiz ${getAccountModel.getAccountData.getAccountList[position].currency.toString()} Hesabım"
        } else {
            binding.vadeliText.text =
                "Vadeli ${getAccountModel.getAccountData.getAccountList[position].currency.toString()} Hesabım"
        }
    }

    private fun createDummyTransactionList(size: Int): Array<GetAccountTransactionList> {
        var x = ArrayList<GetAccountTransactionList>()
        for (i in 0..size) {

            x.add(
                GetAccountTransactionList(
                    "test",
                    "test",
                    "24314",
                    "234234",
                    "test",
                    "test",
                    (-150..150).random().toDouble(),
                    122.2,
                    "t",
                    "t",
                    "t",
                    "t",
                    "t",
                    "t",
                    "t",
                    233.3,
                    "t",
                    "TRY"
                )
            )
        }


        return x.toTypedArray()
    }


    private fun executeSharePopupMenu(inflater: LayoutInflater)
    {
        binding.shareButton.setOnClickListener {

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.share_popup_screen, null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                LinearLayout.LayoutParams.MATCH_PARENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }

            // If API level 23 or higher then execute the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //WINDOW OPEN ANIMATION
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.BOTTOM
                popupWindow.enterTransition = slideIn

                //WINDOW EXIT ANIMATION
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.BOTTOM
                popupWindow.exitTransition = slideOut
            }

            // Get the widgets reference from custom view
            val copyButton = view.findViewById<android.widget.Button>(R.id.copyButton)
            val whatsappButton = view.findViewById<android.widget.Button>(R.id.whatsappButton)
            val wechatButton = view.findViewById<android.widget.Button>(R.id.wechatButton)
            val gmailButton = view.findViewById<android.widget.Button>(R.id.gmailButton)
            val instagramButton = view.findViewById<android.widget.Button>(R.id.instagramButton)
            val twitterButton = view.findViewById<android.widget.Button>(R.id.twitterButton)
            val facebookButton = view.findViewById<android.widget.Button>(R.id.facebookButton)
            val smsButton = view.findViewById<android.widget.Button>(R.id.smsButton)


            //CORRESPONDING BUTTON ONCLICKED EVENT
            copyButton.setOnClickListener {

                //INSTANTIATE THE CLIPBOARD MANAGER
                val clipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE1) as ClipboardManager
                //THE TEXT TO BE COPIED
                val clipData = ClipData.newPlainText(null, binding.accountType.text)
                //COPY THE DATA TO THE SYSTEM'S CLIPBOARD
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(activity, "Text copied to clipboard: " + clipboardManager.primaryClip?.getItemAt(0)?.text.toString(), Toast.LENGTH_LONG).show()

                popupWindow.dismiss()
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            whatsappButton.setOnClickListener {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com/")))
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            wechatButton.setOnClickListener {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wechat.com/")))
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            gmailButton.setOnClickListener {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/intl/tr/gmail/about/#")))
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            instagramButton.setOnClickListener {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/")))
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            twitterButton.setOnClickListener {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/")))
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            facebookButton.setOnClickListener {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/")))
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            smsButton.setOnClickListener {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.messaging&hl=en&gl=US")))
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }

            //WHEN THE POPUP SCREEN IS CLOSED, THIS LISTENER IS EXECUTED
            popupWindow.setOnDismissListener {

                //DO ANYTHING YOU WANT WHEN THE POPUP MENU IS CLOSED

            }

            //IF OUTSIDE OF THE IS CLICKED, MAKE THE SCENE DISAPPEAR
            view?.findViewById<LinearLayout>(R.id.sharePopupScreenName)?.setOnClickListener()
            {
                popupWindow.dismiss()
            }

            //SHOW THE POPUP WINDOW ON THE APP
            TransitionManager.beginDelayedTransition(binding.mainConstraintLayout)
            popupWindow.showAtLocation(
                binding.mainConstraintLayout,
                Gravity.CENTER,
                0,
                0
            )
        }
    }

}