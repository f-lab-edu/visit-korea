package kr.ksw.visitkorea.presentation.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.presentation.main.MainActivity
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

class SplashActivity : ComponentActivity() {

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMap ->
        if(permissionMap[Manifest.permission.ACCESS_COARSE_LOCATION] != true &&
            permissionMap[Manifest.permission.ACCESS_FINE_LOCATION] != true) {
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.location_permission_denied_toast),
                Toast.LENGTH_SHORT
            ).show()
        }
        startMainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisitKoreaTheme {
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Splash Screen",
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
        checkPermission()
    }

    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            startMainActivity()
        } else {
            locationPermissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(
            this,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}