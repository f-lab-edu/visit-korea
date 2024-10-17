package kr.ksw.visitkorea.presentation.home.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.flow.collectLatest
import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.home.component.CultureCard
import kr.ksw.visitkorea.presentation.home.component.MoreButton
import kr.ksw.visitkorea.presentation.home.component.RestaurantCard
import kr.ksw.visitkorea.presentation.home.component.TouristSpotCard
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeState
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeUiEffect
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeViewModel
import kr.ksw.visitkorea.presentation.main.MainRoute
import kr.ksw.visitkorea.presentation.more.MoreActivity
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.homeState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(homeViewModel.homeUiEffect) {
        homeViewModel.homeUiEffect.collectLatest { effect ->
            when(effect) {
                is HomeUiEffect.StartHomeActivity -> {
                    context.startActivity(Intent(
                        context,
                        MoreActivity::class.java
                    ).apply {
                        putExtra("contentType", effect.contentType)
                    })
                }
            }
        }
    }

    HomeScreen(
        homeState = homeState,
        onMoreClick = homeViewModel::startMoreActivity
    )
}

@Composable
fun HomeScreen(
    homeState: HomeState,
    onMoreClick: (ContentType) -> Unit
) {
    val scrollState = rememberScrollState()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp
                            )
                        )
                        .background(color = Color.LightGray),
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(homeState.mainImage)
                        .size(Size.ORIGINAL)
                        .build(),
                    colorFilter = if(homeState.mainImage.isNotEmpty())
                        ColorFilter.tint(Color.LightGray, blendMode = BlendMode.Darken)
                    else
                        null,
                    contentDescription = "Main Content",
                    contentScale = ContentScale.Crop,
                )
                if(homeState.touristSpotList.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = homeState.touristSpotList[0].title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Text(
                                text = homeState.touristSpotList[0].address,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "관광지",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    MoreButton {
                        onMoreClick(ContentType.TOURIST)
                    }
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        count = homeState.touristSpotList.size,
                        key = { it }
                    ) { index ->
                        val touristSpot = homeState.touristSpotList[index]
                        TouristSpotCard(
                            touristSpot.title,
                            touristSpot.address,
                            touristSpot.dist,
                            touristSpot.firstImage
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "문화시설",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    MoreButton {
                        onMoreClick(ContentType.CULTURE)
                    }
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        count = homeState.cultureCenterList.size,
                        key = { it }
                    ) { index ->
                        val cultureCenter = homeState.cultureCenterList[index]
                        CultureCard(
                            Modifier.fillParentMaxWidth(0.5f),
                            title = cultureCenter.title,
                            address = cultureCenter.address,
                            image = cultureCenter.firstImage
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "레포츠",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    MoreButton {
                        onMoreClick(ContentType.LEiSURE)
                    }
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        count = homeState.leisureSportsList.size,
                        key = { it }
                    ) { index ->
                        val leisureSports = homeState.leisureSportsList[index]
                        CultureCard(
                            Modifier.fillParentMaxWidth(0.5f),
                            title = leisureSports.title,
                            address = leisureSports.address,
                            image = leisureSports.firstImage
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "음식점",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    MoreButton {
                        onMoreClick(ContentType.RESTAURANT)
                    }
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        count = homeState.restaurantList.size,
                        key = { it }
                    ) { index ->
                        val restaurant = homeState.restaurantList[index]
                        RestaurantCard(
                            restaurant.title,
                            restaurant.address,
                            restaurant.dist,
                            restaurant.category,
                            restaurant.firstImage,
                            Modifier.width(300.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    VisitKoreaTheme {
        HomeScreen(
            homeState = HomeState(
                mainImage = "https://tong.visitkorea.or.kr/cms/resource/11/3094511_image2_1.jpg"
            ),
            onMoreClick = {  }
        )
    }
}

