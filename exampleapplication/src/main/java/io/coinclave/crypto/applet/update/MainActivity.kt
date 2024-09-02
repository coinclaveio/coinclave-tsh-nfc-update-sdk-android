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
import io.coinclave.crypto.applet.update.dto.ActivateCardResult

class MainActivity : ComponentActivity() {

    private val cardNumber = mutableStateOf("----------------")
    private val cardHolder = mutableStateOf("")
    private val cvv = mutableStateOf("---")
    private val expiryDate = mutableStateOf("-/-")
    private val brand = mutableStateOf("")
    private val activateButtonEnabled = mutableStateOf(true)

    val updateAppletNfcService: UpdateAppletNfcService =
        UpdateAppletNfcServiceActivityImpl(R.layout.fragment_attach_card)

    val nfcActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            result?.let {
                it.data?.let {
                    var result = it.getSerializableExtra(Intent.EXTRA_SUBJECT)
                    if (result is ActivateCardResult) {
                        AlertDialog.Builder(this)
                            .setMessage("Congratulations - your card is now activated!")
                            .setPositiveButton("OK") {dialog, which -> }
                            .create().show()
                        cardNumber.value = result.pan
                        cardHolder.value = "John Doe"
                        expiryDate.value = result.expiryDate
                        cvv.value = "***"
                        brand.value = "VISA"
                        activateButtonEnabled.value = false
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
                        cardNumber = cardNumber.value,
                        cardHolder = cardHolder.value,
                        expires = expiryDate.value,
                        cvv = cvv.value,
                        brand = brand.value,
                        maskNumber = false
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                            enabled = activateButtonEnabled.value,
                            onClick = {
                                startNfc(
                                    applicationContext,
                                    updateAppletNfcService,
                                    nfcActivityResult
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

fun startNfc(
    context: Context,
    updateAppletNfcService: UpdateAppletNfcService,
    nfcLauncher: ActivityResultLauncher<Intent>,
) {
    updateAppletNfcService.checkNeedAppletUpdate(context, nfcLauncher);
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
                cardNumber = "123456789012345",
                cardHolder = "John Doe",
                expires = "12/34",
                cvv = "123",
                brand = "VISA",
                maskNumber = true
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