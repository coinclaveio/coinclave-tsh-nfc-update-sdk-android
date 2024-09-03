package io.coinclave.crypto.applet.update

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import io.coinclave.crypto.applet.update.network.models.CheckAppletVersionData

interface UpdateAppletNfcService {

    fun updateApplet(context: Context,
                     nfcLauncher: ActivityResultLauncher<Intent>,
                     updateData: CheckAppletVersionData)

    fun checkNeedAppletUpdate(context: Context,
                       nfcLauncher: ActivityResultLauncher<Intent>)

}