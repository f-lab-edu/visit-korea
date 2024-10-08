package kr.ksw.visitkorea.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisitKoreaTheme {

            }
        }
    }
}