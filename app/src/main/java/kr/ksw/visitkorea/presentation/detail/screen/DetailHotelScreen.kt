package kr.ksw.visitkorea.presentation.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.model.HotelDetail
import kr.ksw.visitkorea.domain.usecase.model.HotelRoomDetail
import kr.ksw.visitkorea.domain.usecase.util.toDistForUi
import kr.ksw.visitkorea.presentation.detail.component.DetailHotelCard
import kr.ksw.visitkorea.presentation.detail.component.DetailImageRow
import kr.ksw.visitkorea.presentation.detail.component.DetailIntroContent
import kr.ksw.visitkorea.presentation.detail.component.TitleView
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelActions
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelState
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun DetailHotelScreen(
    viewModel: DetailHotelViewModel
) {
    val detailHotelState by viewModel.hotelDetailState.collectAsState()
    DetailHotelScreen(
        hotelDetailState = detailHotelState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun DetailHotelScreen(
    hotelDetailState: DetailHotelState,
    onAction: (DetailHotelActions) -> Unit
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.2f)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 24.dp,
                                bottomEnd = 24.dp
                            )
                        )
                        .background(color = Color.LightGray),
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(hotelDetailState.firstImage)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = "Detail Image",
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TitleView(
                hotelDetailState.title,
                hotelDetailState.address,
                hotelDetailState.dist?.toDistForUi()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                InfoButton(
                    modifier = Modifier
                        .weight(1.0f),
                    title = "시설 안내"
                ) {
                    onAction(DetailHotelActions.OnClickFacilityInfoButton)
                    scope.launch {
                        scrollState.scrollToItem(index = 0)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                InfoButton(
                    modifier = Modifier
                        .weight(1.0f),
                    title = "객실 안내"
                ) {
                    onAction(DetailHotelActions.OnClickRoomInfoButton)
                    scope.launch {
                        scrollState.scrollToItem(index = 0)
                    }
                }
            }
            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                if(hotelDetailState.showFacilityInfo) {
                    item {
                        DetailIntroView(
                            hotelDetail = hotelDetailState.hotelDetail
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        DetailImageRow(hotelDetailState.images)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                if(hotelDetailState.showRoomDetail) {
                    if(hotelDetailState.hotelRoomDetail.isEmpty()) {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                text = "객실 정보 없음",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        items(
                            count = hotelDetailState.hotelRoomDetail.size,
                            key = { it }
                        ) { index ->
                            DetailHotelCard(hotelDetailState.hotelRoomDetail[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoButton(
    modifier: Modifier,
    title: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .defaultMinSize(
                minHeight = 10.dp
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        ),
        contentPadding = PaddingValues(vertical = 6.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
            )
        }
    }
}

@Composable
private fun DetailIntroView(
    hotelDetail: HotelDetail
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "시설안내",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        hotelDetail.checkInTime?.let {
            DetailIntroContent(
                "체크인",
                it
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        hotelDetail.checkOutTime?.let {
            DetailIntroContent(
                "체크아웃",
                it
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        hotelDetail.subFacility?.let {
            DetailIntroContent(
                "쉬는날",
                it
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailHotelScreenPreview() {
    VisitKoreaTheme {
        DetailHotelScreen(
            hotelDetailState = DetailHotelState(
                title = "숙박업체 이름",
                firstImage = "",
                address = "주소",
                dist = "400.415876897",
                hotelDetail = HotelDetail(
                    checkInTime = "15:00",
                    checkOutTime = "익일 12:00",
                    subFacility = "미팅룸, 비즈니스 코너, 피트니스 센터, 세탁실",
                    reservationUrl = "https://www.hotel.com",
                    tel = "070-0000-0000"
                ),
                hotelRoomDetail = listOf(
                    HotelRoomDetail(
                        roomTitle = "프리미어 스탠다드",
                        roomOffSeasonMinFee1 = "60,000",
                        roomOffSeasonMinFee2 = "70,000",
                        roomBaseCount = "2",
                        roomMaxCount = "5",
                        roomSize2 = "46",
                        roomBathFacility = "N",
                        roomTv = "N",
                        roomInternet = "Y",
                        roomPc = "Y",
                        roomHairdryer = "Y",
                        roomRefrigerator = "Y"
                    ),
                    HotelRoomDetail(
                        roomTitle = "프리미어 스탠다드",
                        roomOffSeasonMinFee1 = "60,000",
                        roomOffSeasonMinFee2 = "70,000",
                        roomBaseCount = "2",
                        roomMaxCount = "5",
                        roomSize2 = "46",
                        roomBathFacility = "N",
                        roomTv = "N",
                        roomInternet = "Y",
                        roomPc = "Y",
                        roomHairdryer = "Y",
                        roomRefrigerator = "Y"
                    )
                ),
                showFacilityInfo = false,
                showRoomDetail = true
            )
        ) {

        }
    }
}