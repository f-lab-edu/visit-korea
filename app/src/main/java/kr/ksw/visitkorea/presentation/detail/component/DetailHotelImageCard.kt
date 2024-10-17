package kr.ksw.visitkorea.presentation.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.domain.model.HotelRoomDetail
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailHotelImageCard(
    hotelRoomDetail: HotelRoomDetail
) {
    val pagerState = rememberPagerState(pageCount = {
        hotelRoomDetail.roomImages.size
    })
    Card(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Box {
                HorizontalPager(
                    key = { index ->
                        hotelRoomDetail.roomImages[index]
                    },
                    state = pagerState
                ) { page ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.4f)
                            .background(color = Color.LightGray),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(hotelRoomDetail.roomImages[page])
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = "Culture Spot Image",
                        contentScale = ContentScale.Crop,
                    )
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(horizontal = 10.dp),
                    text = "${pagerState.currentPage+1}/${hotelRoomDetail.roomImages.size}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = hotelRoomDetail.roomTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "${hotelRoomDetail.roomOffSeasonMinFee1} ~ ${hotelRoomDetail.roomOffSeasonMinFee2} 원",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Blue
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1.0f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.twotone_people_alt_24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${hotelRoomDetail.roomBaseCount}~${hotelRoomDetail.roomMaxCount}명",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1.0f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.rounded_zoom_out_map_24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if(hotelRoomDetail.roomSize2 == "0")
                                "${hotelRoomDetail.roomSize}평"
                            else
                                "${hotelRoomDetail.roomSize2}㎡",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                FlowRow(
                    modifier = Modifier.
                    fillMaxWidth(),
                ) {
                    if(hotelRoomDetail.roomBathFacility == "Y") {
                        HotelOptionItem(
                            R.drawable.outline_bathtub_24,
                            "목욕시설"
                        )
                    }
                    if(hotelRoomDetail.roomTv == "Y") {
                        HotelOptionItem(
                            R.drawable.outline_tv_24,
                            "TV"
                        )
                    }
                    if(hotelRoomDetail.roomPc == "Y") {
                        HotelOptionItem(
                            R.drawable.outline_computer_24,
                            "PC"
                        )
                    }
                    if(hotelRoomDetail.roomInternet == "Y") {
                        HotelOptionItem(
                            R.drawable.baseline_wifi_24,
                            "인터넷"
                        )
                    }
                    if(hotelRoomDetail.roomHairdryer == "Y") {
                        HotelOptionItem(
                            R.drawable.custom_hairdryer_24,
                            "드라이기"
                        )
                    }
                    if(hotelRoomDetail.roomRefrigerator == "Y") {
                        HotelOptionItem(
                            R.drawable.custom_refrigerator_24,
                            "냉장고"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailHotelImageCardPreview() {
    VisitKoreaTheme {
        Surface {
            DetailHotelImageCard(
                hotelRoomDetail = HotelRoomDetail(
                    roomTitle = "프리미어 스탠다드",
                    roomOffSeasonMinFee1 = "60,000",
                    roomOffSeasonMinFee2 = "70,000",
                    roomBaseCount = "2",
                    roomMaxCount = "5",
                    roomSize2 = "46",
                    roomBathFacility = "Y",
                    roomTv = "Y",
                    roomInternet = "Y",
                    roomPc = "Y",
                    roomHairdryer = "Y",
                    roomRefrigerator = "Y",
                    roomImages = listOf(
                        "", ""
                    )
                )
            )
        }
    }
}