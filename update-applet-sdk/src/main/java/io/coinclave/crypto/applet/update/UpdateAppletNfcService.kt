package io.coinclave.crypto.applet.update

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface UpdateAppletNfcService {

//    fun updateApplet(context: Context,
//                     nfcLauncher: ActivityResultLauncher<Intent>)

    fun checkNeedAppletUpdate(context: Context,
                       nfcLauncher: ActivityResultLauncher<Intent>)

}