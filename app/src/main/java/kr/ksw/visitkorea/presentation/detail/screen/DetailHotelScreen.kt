package kr.ksw.visitkorea.presentation.detail.screen

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kr.ksw.visitkorea.BuildConfig
import kr.ksw.visitkorea.domain.model.HotelDetail
import kr.ksw.visitkorea.domain.model.HotelRoomDetail
import kr.ksw.visitkorea.domain.usecase.util.toDistForUi
import kr.ksw.visitkorea.presentation.detail.component.DetailHotelCard
import kr.ksw.visitkorea.presentation.detail.component.DetailHotelImageCard
import kr.ksw.visitkorea.presentation.detail.component.DetailImageRow
import kr.ksw.visitkorea.presentation.detail.component.DetailImageViewPager
import kr.ksw.visitkorea.presentation.detail.component.DetailIntroContent
import kr.ksw.visitkorea.presentation.detail.component.DetailTitleView
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelActions
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelState
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelUIEffect
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailHotelViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme
import java.net.URLEncoder

@Composable
fun DetailHotelScreen(
    viewModel: DetailHotelViewModel
) {
    val detailHotelState by viewModel.hotelDetailState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(viewModel.hotelDetailUIEffect) {
        viewModel.hotelDetailUIEffect.collectLatest { effect ->
            when(effect) {
                is DetailHotelUIEffect.OpenMapApplication -> {
                    val name = async {
                        URLEncoder.encode(effect.name, "UTF-8")
                    }.await()
                    val url = "nmap://place?lat=${effect.lat}&lng=${effect.lng}&name=$name&appname=${BuildConfig.APPLICATION_ID}"

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)

                    val list: List<ResolveInfo> = context.packageManager.queryIntentActivities(
                        intent,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )
                    if (list.isEmpty()) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=com.nhn.android.nmap")
                            )
                        )
                    } else {
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    BackHandler(
        enabled = detailHotelState.viewPagerOpen
    ) {
        viewModel.onAction(
            DetailHotelActions.ClickBackButtonWhenViewPagerOpened
        )
    }

    DetailHotelScreen(
        hotelDetailState = detailHotelState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailHotelScreen(
    hotelDetailState: DetailHotelState,
    onAction: (DetailHotelActions) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn {
                item {
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
                }
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface
                            )
                    ) {
                        DetailTitleView(
                            hotelDetailState.title,
                            hotelDetailState.address,
                            hotelDetailState.dist?.toDistForUi(),
                            if(hotelDetailState.hotelDetail.reservationUrl.isNullOrEmpty())
                                hotelDetailState.homePage
                            else hotelDetailState.hotelDetail.reservationUrl,
                            hotelDetailState.hotelDetail.tel ?: ""
                        ) {
                            onAction(DetailHotelActions.ClickViewMapButton)
                        }
                        InfoButtonHeader(
                            onAction = onAction
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                if(hotelDetailState.showFacilityInfo) {
                    item {
                        DetailIntroView(
                            hotelDetail = hotelDetailState.hotelDetail
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        DetailImageRow(
                            images = hotelDetailState.images,
                            onImageClick = { index ->
                                onAction(DetailHotelActions.ClickDetailImages(
                                    selectedImage = index
                                ))
                            }
                        )
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
                            val roomDetail = hotelDetailState.hotelRoomDetail[index]
                            if(roomDetail.roomImages.isEmpty()) {
                                DetailHotelCard(roomDetail)
                            } else {
                                DetailHotelImageCard(
                                    hotelRoomDetail = roomDetail
                                ) { imageIndex ->
                                    onAction(DetailHotelActions.ClickRoomDetailImages(
                                        selectedImage = imageIndex,
                                        selectedRoomIndex = index
                                    ))
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
        if(hotelDetailState.viewPagerOpen) {
            DetailImageViewPager(
                selectedImage = hotelDetailState.selectedImage,
                images = hotelDetailState.viewPagerImages
            )
        }
    }
}

@Composable
private fun InfoButtonHeader(
    onAction: (DetailHotelActions) -> Unit,
) {
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
        }
        Spacer(modifier = Modifier.width(12.dp))
        InfoButton(
            modifier = Modifier
                .weight(1.0f),
            title = "객실 안내"
        ) {
            onAction(DetailHotelActions.OnClickRoomInfoButton)
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
            .padding(
                start = 16.dp,
                end = 16.dp
            )
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
                "부대시설",
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
                    ),
                ),
                showFacilityInfo = false,
                showRoomDetail = true
            )
        ) {

        }
    }
}