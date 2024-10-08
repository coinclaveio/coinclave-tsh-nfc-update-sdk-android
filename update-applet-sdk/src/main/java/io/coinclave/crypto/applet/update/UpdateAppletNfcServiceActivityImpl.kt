package io.coinclave.crypto.applet.update

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import io.coinclave.crypto.applet.update.network.models.CheckAppletVersionData
import io.coinclave.crypto.applet.update.nfc.commands.aggregates.CheckAppletVersionCommandsAggregate
import io.coinclave.crypto.applet.update.nfc.commands.aggregates.CommandAggregateCreator
import io.coinclave.crypto.applet.update.nfc.commands.aggregates.UpdateAppletCommandsAggregate

class UpdateAppletNfcServiceActivityImpl(
    private val smartCardLayout: Int?,
) : UpdateAppletNfcService {

    private val commandAggregateCreator = CommandAggregateCreator()

    override fun updateApplet(context: Context,
                              nfcLauncher: ActivityResultLauncher<Intent>,
                              updateData: CheckAppletVersionData) {
        nfcLauncher.launch(Intent(
            context,
            NfcHandlerActivity::class.java
        ).apply {
            putExtra(
                Intent.EXTRA_SUBJECT,
                UpdateAppletCommandsAggregate(
                    commandAggregateCreator.createUpdateAppletCommandsAggregate(updateData.oldVersion, updateData.newVersion)
                )
            )
            putExtra(PARAM_SMART_CARD_CONTENT, smartCardLayout)
        })
    }

    override fun checkNeedAppletUpdate(
        context: Context,
        nfcLauncher: ActivityResultLauncher<Intent>
    ) {
        nfcLauncher.launch(Intent(
            context,
            NfcHandlerActivity::class.java
        ).apply {
            putExtra(
                Intent.EXTRA_SUBJECT,
                CheckAppletVersionCommandsAggregate(
                    commandAggregateCreator.createCheckAppletVersionCommandsAggregate()
                )
            )
            putExtra(PARAM_SMART_CARD_CONTENT, smartCardLayout)
        })
    }

}