package kr.ksw.visitkorea.presentation.more

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.more.screen.MoreScreen
import kr.ksw.visitkorea.presentation.more.viewmodel.MoreViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@AndroidEntryPoint
class MoreActivity : ComponentActivity() {
    private val viewModel: MoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentType = (intent.getSerializableExtra("contentType") as? ContentType) ?: ContentType.TOURIST
        setContent {
            VisitKoreaTheme {
                MoreScreen(
                    viewModel = viewModel,
                    contentType = contentType
                ) {
                    finish()
                }
            }
        }
        viewModel.getMoreListByContentType(contentType.contentTypeId)
    }
}