package com.example.intertech_account.view_model

import android.graphics.Bitmap
import android.graphics.BitmapRegionDecoder
import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.lifecycle.HiltViewModel

class QrCodeGenerateViewModel():ViewModel() {


    private var parameters:ArrayList<String> = arrayListOf()

    fun addParameter(parameter:String){
        //TODO burada parametre kontrolleri yapilacak eger gecerse ekleme islemi yapilmayacak ise log kayidi tutulacak ekrana hata mesaji bastirilacak
        parameters.add(parameter)
    }

    fun getQrCode():Bitmap?{
        //TODO burada kontrol yapilabilir
        //TODO optional donmek yerine kontrol yapilir bos ise hata resmi donulur

        return createQrCode()
    }

     private fun createQrCode(): Bitmap? {
        var qrCodeContent = ""
        for(index in parameters){
            qrCodeContent+=index
        }
        val size = 512 //pixels
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size, hints)

        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }

    }
}
