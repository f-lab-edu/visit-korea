package kr.ksw.visitkorea.presentation.more

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ksw.visitkorea.presentation.more.screen.MoreScreen
import kr.ksw.visitkorea.presentation.more.viewmodel.MoreViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@AndroidEntryPoint
class MoreActivity : ComponentActivity() {
    private val viewModel: MoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisitKoreaTheme {
                MoreScreen(
                    viewModel = viewModel,
                    contentTypeId = intent.getStringExtra("contentTypeId") ?: ""
                ) {
                    finish()
                }
            }
        }
        viewModel.getMoreListByContentType(intent.getStringExtra("contentTypeId") ?: "")
    }
}