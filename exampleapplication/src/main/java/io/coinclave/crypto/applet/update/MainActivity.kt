package io.coinclave.crypto.applet.update

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.coinclave.crypto.applet.update.dto.BlankResult
import io.coinclave.crypto.applet.update.network.models.CheckAppletVersionData

class MainActivity : ComponentActivity() {

    private val checkUpdateAppletButtonEnabled = mutableStateOf(true)

    val updateAppletNfcService: UpdateAppletNfcService =
        UpdateAppletNfcServiceActivityImpl(R.layout.fragment_attach_card)

    val nfcCheckUpdateResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            result?.let {
                it.data?.let {
                    var result = it.getSerializableExtra(Intent.EXTRA_SUBJECT)
                    if (result is CheckAppletVersionData) {
                        val message =
                            if (result.needUpdate) ("New version of applet exists. \nYou version "
                                    + result.oldVersion
                                    + ". \nNew version "
                                    + result.newVersion
                                    + ". \nDo you want to update?")
                            else ("You have actual version applet: " + result.oldVersion)
                        val dialogBuilder = AlertDialog.Builder(this)
                            .setMessage(
                                message
                            )
                        dialogBuilder
                            .setPositiveButton("OK") { dialog, which ->
                                if (result.needUpdate)
                                    startUpdateNfc(
                                        applicationContext,
                                        updateAppletNfcService,
                                        nfcUpdateResult,
                                        result
                                    )
                            }
                        if (result.needUpdate)
                            dialogBuilder.setNeutralButton("No") { dialog, which -> }
                        dialogBuilder.create().show()
                        checkUpdateAppletButtonEnabled.value = true
                    }
                }
            }
        }

    val nfcUpdateResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            result?.let {
                it.data?.let {
                    var result = it.getSerializableExtra(Intent.EXTRA_SUBJECT)
                    if (result is BlankResult) {
                        AlertDialog.Builder(this)
                            .setMessage("Applet is updated.")
                            .setPositiveButton("OK") { dialog, which -> }
                            .create().show()
                        checkUpdateAppletButtonEnabled.value = true
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContent {
            Surface {
                Image(
                    modifier = Modifier.fillMaxWidth(1f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.big_logo),
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    BankCardUi(
                        baseColor = Color(0xFFFF9800),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                            enabled = checkUpdateAppletButtonEnabled.value,
                            onClick = {
                                startCheckUpdateNfc(
                                    applicationContext,
                                    updateAppletNfcService,
                                    nfcCheckUpdateResult
                                )
                            }) {
                            Text("Check update applet")
                        }
                    }
                }
            }
        }
    }

}

fun startCheckUpdateNfc(
    context: Context,
    updateAppletNfcService: UpdateAppletNfcService,
    nfcLauncher: ActivityResultLauncher<Intent>
) {
    updateAppletNfcService.checkNeedAppletUpdate(context, nfcLauncher)
}

fun startUpdateNfc(
    context: Context,
    updateAppletNfcService: UpdateAppletNfcService,
    nfcLauncher: ActivityResultLauncher<Intent>,
    updateData: CheckAppletVersionData
) {
    updateAppletNfcService.updateApplet(context, nfcLauncher, updateData)
}

@Composable
@Preview
fun MainActivityPreview() {
    Surface {
        Image(
            modifier = Modifier.fillMaxWidth(1f),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.big_logo),
            contentDescription = stringResource(id = R.string.app_name)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            BankCardUi(
                baseColor = Color(0xFFFF9800),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    onClick = { /*TODO*/ }) {
                    Text("Check update applet")
                }
            }
        }
    }
}