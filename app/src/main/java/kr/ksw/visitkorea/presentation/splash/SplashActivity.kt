package kr.ksw.visitkorea.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.presentation.main.MainActivity
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

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
        observeSideEffect()
        viewModel.checkPermission(this)
        viewModel.initAreaCode(applicationContext)
    }

    private fun observeSideEffect() {
        lifecycleScope.launch {
            viewModel.sideEffect.collectLatest { effect ->
                when(effect) {
                    SplashSideEffect.PermissionDenied -> {
                        Toast.makeText(
                            this@SplashActivity,
                            getString(R.string.location_permission_denied_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    SplashSideEffect.StartMainActivity -> {
                        startMainActivity()
                    }
                }
            }
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