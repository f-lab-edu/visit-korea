package kr.ksw.visitkorea.presentation.hotel.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kr.ksw.visitkorea.domain.usecase.model.CommonCardModel
import kr.ksw.visitkorea.presentation.home.component.CultureCard
import kr.ksw.visitkorea.presentation.hotel.viewmodel.HotelViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun HotelScreen(
    viewModel: HotelViewModel = hiltViewModel()
) {
    val hotelState by viewModel.hotelState.collectAsState()
    val lazyItem = hotelState.hotelCardModelFlow.collectAsLazyPagingItems()
    HotelScreen(
        lazyItem
    )
}

@Composable
fun HotelScreen(
    hotelCardModels: LazyPagingItems<CommonCardModel>,
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 10.dp
                    )
            ) {
                Text(
                    text = "숙박",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "주변에서 머물곳을 찾아보세요!",
                    fontSize = 22.sp,
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(
                    count = hotelCardModels.itemCount,
                    key = { index ->
                        hotelCardModels[index]?.contentId?.toInt() ?: index
                    }
                ) { index ->
                    val hotel = hotelCardModels[index]
                    hotel?.run {
                        CultureCard(
                            title = hotel.title,
                            address = hotel.address,
                            image = hotel.firstImage
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HotelScreenPreview() {
    VisitKoreaTheme {
        HotelScreen()
    }
}