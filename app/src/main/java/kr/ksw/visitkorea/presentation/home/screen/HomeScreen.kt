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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import kr.ksw.visitkorea.domain.common.TYPE_CULTURE
import kr.ksw.visitkorea.domain.common.TYPE_LEiSURE
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.detail.DetailActivity
import kr.ksw.visitkorea.presentation.component.CommonCard
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.home.component.MoreButton
import kr.ksw.visitkorea.presentation.home.component.RestaurantCard
import kr.ksw.visitkorea.presentation.home.component.TouristSpotCard
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeActions
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeState
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeUiEffect
import kr.ksw.visitkorea.presentation.home.viewmodel.HomeViewModel
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
                is HomeUiEffect.StartMoreActivity -> {
                    context.startActivity(Intent(
                        context,
                        MoreActivity::class.java
                    ).apply {
                        putExtra("contentType", effect.contentType)
                    })
                }
                is HomeUiEffect.StartDetailActivity -> {
                    context.startActivity(Intent(
                        context,
                        DetailActivity::class.java
                    ).apply {
                        putExtra("detail", effect.data)
                    })
                }
            }
        }
    }

    HomeScreen(
        homeState = homeState,
        onMoreClick = homeViewModel::onAction,
        onItemClick = homeViewModel::onAction
    )
}

@Composable
fun HomeScreen(
    homeState: HomeState,
    onMoreClick: (HomeActions) -> Unit,
    onItemClick: (HomeActions) -> Unit
) {
    val scrollState = rememberScrollState()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if(homeState.mainPagerItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp
                            )
                        )
                        .background(color = Color.LightGray),  // change to shimmer effect
                )
            } else {
                val pagerState = rememberPagerState(
                    pageCount = { homeState.mainPagerItems.size }
                )
                HorizontalPager(
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
                    state = pagerState,
                    key = { index ->
                        homeState.mainPagerItems[index].image
                    },
                ) { page ->
                    val pageData = homeState.mainPagerItems[page]
                    Box(
                        contentAlignment = Alignment.BottomStart
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.0f),
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(pageData.image)
                                .size(Size.ORIGINAL)
                                .build(),
                            colorFilter = if(pageData.image.isNotEmpty())
                                ColorFilter.tint(Color.LightGray, blendMode = BlendMode.Darken)
                            else
                                null,
                            contentDescription = "Main Contents",
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = pageData.title,
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
                                SingleLineText(
                                    text = pageData.address,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
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
                        onMoreClick(HomeActions.ClickMoreButton(
                            ContentType.TOURIST
                        ))
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
                        ) {
                            onItemClick(
                                HomeActions.ClickCardItem(DetailParcel(
                                    title = touristSpot.title,
                                    firstImage = touristSpot.firstImage,
                                    address = touristSpot.address,
                                    dist = touristSpot.dist,
                                    contentId = touristSpot.contentId,
                                    contentTypeId = TYPE_TOURIST_SPOT
                                ))
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
                        text = "문화시설",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    MoreButton {
                        onMoreClick(HomeActions.ClickMoreButton(
                            ContentType.CULTURE
                        ))
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
                        CommonCard(
                            Modifier.fillParentMaxWidth(0.5f),
                            title = cultureCenter.title,
                            address = cultureCenter.address,
                            image = cultureCenter.firstImage,
                            contentTypeId = cultureCenter.contentTypeId
                        ) {
                            onItemClick(HomeActions.ClickCardItem(
                                DetailParcel(
                                    title = cultureCenter.title,
                                    address = cultureCenter.address,
                                    firstImage = cultureCenter.firstImage,
                                    contentId = cultureCenter.contentId,
                                    contentTypeId = TYPE_CULTURE
                                )
                            ))
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
                        text = "레포츠",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    MoreButton {
                        onMoreClick(HomeActions.ClickMoreButton(
                            ContentType.LEiSURE
                        ))
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
                        CommonCard(
                            Modifier.fillParentMaxWidth(0.5f),
                            title = leisureSports.title,
                            address = leisureSports.address,
                            image = leisureSports.firstImage,
                            contentTypeId = leisureSports.contentTypeId
                        ) {
                            onItemClick(HomeActions.ClickCardItem(
                                DetailParcel(
                                    title = leisureSports.title,
                                    address = leisureSports.address,
                                    firstImage = leisureSports.firstImage,
                                    contentId = leisureSports.contentId,
                                    contentTypeId = TYPE_LEiSURE
                                )
                            ))
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
                        text = "음식점",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    MoreButton {
                        onMoreClick(HomeActions.ClickMoreButton(
                            ContentType.RESTAURANT
                        ))
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
                            restaurant.firstImage2,
                            Modifier.width(300.dp)
                        ) {
                            onItemClick(HomeActions.ClickCardItem(
                                DetailParcel(
                                    title = restaurant.title,
                                    firstImage = restaurant.firstImage,
                                    address = restaurant.address,
                                    dist = restaurant.dist,
                                    contentId = restaurant.contentId,
                                    contentTypeId = TYPE_RESTAURANT
                                )
                            ))
                        }
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
                mainPagerItems = emptyList()
            ),
            onMoreClick = {  },
            onItemClick = { _ ->

            }
        )
    }
}

