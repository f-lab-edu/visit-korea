package kr.ksw.visitkorea.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.detail.screen.DetailScreen
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisitKoreaTheme {
                DetailScreen(viewModel)
            }
        }

        val detail = intent.getParcelableExtra<DetailParcel>("detail")
        if(detail != null) {
            viewModel.initDetail(detail)
        }
    }
}