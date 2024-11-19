package kr.ksw.visitkorea.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ksw.visitkorea.domain.common.TYPE_HOTEL
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.detail.screen.DetailHotelScreen
import kr.ksw.visitkorea.presentation.detail.screen.DetailScreen
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelViewModel
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private val hotelViewModel :DetailHotelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detail = intent.getParcelableExtra<DetailParcel>("detail")

        setContent {
            VisitKoreaTheme {
                when(detail?.contentTypeId) {
                    TYPE_HOTEL -> DetailHotelScreen(hotelViewModel)
                    else -> DetailScreen(viewModel)
                }
            }
        }

        if(detail != null) {
            initViewModel(detail)
        }
    }

    private fun initViewModel(detailParcel: DetailParcel) {
        if(detailParcel.contentTypeId == TYPE_HOTEL) {
            hotelViewModel.initDetail(detailParcel)
            return
        }
        viewModel.initDetail(detailParcel)
    }
}