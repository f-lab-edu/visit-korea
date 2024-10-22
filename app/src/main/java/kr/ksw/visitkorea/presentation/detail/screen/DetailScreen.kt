package kr.ksw.visitkorea.presentation.detail.screen

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kr.ksw.visitkorea.BuildConfig
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.domain.common.TYPE_FESTIVAL
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.domain.model.CommonDetail
import kr.ksw.visitkorea.presentation.common.convertHtmlToString
import kr.ksw.visitkorea.presentation.detail.component.DetailImageRow
import kr.ksw.visitkorea.presentation.detail.component.DetailImageViewPager
import kr.ksw.visitkorea.presentation.detail.component.DetailIntroContent
import kr.ksw.visitkorea.presentation.detail.component.DetailTitleView
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailActions
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailState
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailUIEffect
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme
import java.net.URLEncoder


@Composable
fun DetailScreen(
    viewModel: DetailViewModel
) {
    val detailState by viewModel.detailState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(viewModel.detailUIEffect) {
        viewModel.detailUIEffect.collectLatest { effect ->
            when(effect) {
                is DetailUIEffect.OpenMapApplication -> {
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
        enabled = detailState.viewPagerOpen
    ) {
        viewModel.onAction(
            DetailActions.ClickBackButtonWhenViewPagerOpened
        )
    }

    DetailScreen(
        detailState = detailState,
        onClick = viewModel::onAction
    )
}

@Composable
private fun DetailScreen(
    detailState: DetailState,
    onClick: (DetailActions) -> Unit
) {
    val scrollState = rememberScrollState()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 24.dp,
                                bottomEnd = 24.dp
                            )
                        )
                        .background(color = Color.LightGray),
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(detailState.firstImage)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = "Detail Image",
                    contentScale = ContentScale.Crop,
                )
                when(detailState.contentTypeId) {
                    TYPE_TOURIST_SPOT,
                    TYPE_FESTIVAL -> {
                        Icon(
                            if(detailState.isFavorite)
                                Icons.Default.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite Icon",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                                .size(24.dp)
                                .clickable {
                                    if (detailState.isFavorite) {
                                        onClick(
                                            DetailActions.ClickFavoriteIconDelete(
                                                detailState.contentId
                                            )
                                        )
                                    } else {
                                        onClick(
                                            DetailActions.ClickFavoriteIconUpsert(
                                                FavoriteEntity(
                                                    title = detailState.title,
                                                    address = detailState.address,
                                                    dist = detailState.dist,
                                                    firstImage = detailState.firstImage,
                                                    contentId = detailState.contentId,
                                                    contentTypeId = detailState.contentTypeId,
                                                    eventStartDate = detailState.eventStartDate,
                                                    eventEndDate = detailState.eventEndDate
                                                )
                                            )
                                        )
                                    }
                                },
                            tint = Color.Red
                        )
                        if(detailState.contentTypeId == TYPE_FESTIVAL &&
                            detailState.eventStartDate != null &&
                            detailState.eventEndDate != null) {
                            FestivalDateView(
                                detailState.eventStartDate,
                                detailState.eventEndDate
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            DetailTitleView(
                detailState.title,
                detailState.address,
                detailState.dist,
            ) {
                onClick(DetailActions.ClickViewMapButton)
            }
            Spacer(modifier = Modifier.height(16.dp))
            OverView(detailState.detailCommon.overview)
            Spacer(modifier = Modifier.height(16.dp))
            DetailIntroView(
                contentTypeId = detailState.contentTypeId,
                detailIntro = detailState.detailIntro
            )
            Spacer(modifier = Modifier.height(16.dp))
            DetailImageRow(
                images = detailState.images,
                onImageClick = { index ->
                    onClick(DetailActions.ClickDetailImages(
                        selectedImage = index
                    ))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(detailState.viewPagerOpen) {
            DetailImageViewPager(
                selectedImage = detailState.selectedImage,
                images = detailState.images.map { imageDTO ->
                    imageDTO.originImgUrl
                }
            )
        }
    }
}

@Composable
private fun FestivalDateView(
    eventStartDate: String,
    eventEndDate: String
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .border(
                2.dp,
                Color.DarkGray,
                RoundedCornerShape(
                    bottomEnd = 24.dp
                )
            )
            .background(
                Color.Black.copy(alpha = 0.5f),
                RoundedCornerShape(
                    bottomEnd = 24.dp
                )
            )
            .padding(
                vertical = 28.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = eventStartDate,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = Color.White,
            letterSpacing = (-0.6).sp
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            thickness = 2.dp,
            color = Color.White
        )
        Text(
            text = eventEndDate,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = Color.White,
            letterSpacing = (-0.6).sp
        )
    }
}

@Composable
private fun OverView(
    overView: String
) {
    var maxLines by remember {
        mutableIntStateOf(6)
    }
    var showMore by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "개요",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = overView.convertHtmlToString(),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                showMore = result.didOverflowHeight
            },
            fontSize = 14.sp
        )
        if(showMore) {
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .align(Alignment.End)
                    .clickable {
                        maxLines = Integer.MAX_VALUE
                    },
                text = "더보기",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun DetailIntroView(
    contentTypeId: String,
    detailIntro: CommonDetail
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = if(contentTypeId == TYPE_FESTIVAL) "행사안내" else "시설안내",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        detailIntro.parking?.let {
            DetailIntroContent(
                "주차시설",
                it
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        detailIntro.time?.let {
            DetailIntroContent(
                if(contentTypeId == TYPE_RESTAURANT) "영업시간" else "이용시간",
                it
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        detailIntro.restDate?.let {
            DetailIntroContent(
                "쉬는날",
                it
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        detailIntro.fee?.let {
            DetailIntroContent(
                "이용요금",
                it
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        detailIntro.menus?.let {
            DetailIntroContent(
                "대표메뉴",
                it
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        detailIntro.place?.let {
            DetailIntroContent(
                "행사장소",
                it
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    VisitKoreaTheme {
        DetailScreen(
            DetailState(
                title = "타이틀 영역",
                address = "주소 영역",
                dist = "거리 표기",
                detailCommon = DetailCommonDTO(
                    homepage = "",
                    overview = "수원박물관은 지하 1층, 지상 2층으로 수원역사박물관, 한국서예박물관 등 2개의 상설전시관과 특별전시실로 구성되어 있다. 오감으로 즐기고 배우는 어린이체험실, 다채로운 교육 프로그램 등이 운영되고, 소장유물은 수원지역에서 발굴된 유물, 구입 및 기증받은 유물 등으로 48,000여 점을 소장하고 있다. 수원역사박물관은 수원의 유구한 역사와 문화를 과거·현재·미래의 시점과 주제별로 구성하였으며, 크게 네 부분으로 구성된 전시관은 ‘수원의 자연환경’, ‘선사·역사시대의 변천사’, ‘수원로의 개설’, ‘60년대 수원 만나기’, ‘근대 수원의 문화’로 과거의 역사와 문화뿐만 아니라 현대의 발전하는 역동적인 수원의 모습을 다양하게 보여주고 있다. 한국서예박물관은 지방자치단체에서는 최초로 건립한 서예 전문 박물관으로, 우리나라 서예사를 한눈에 살펴볼 수 있다. 전시는 유물의 시기와 특성에 따라 각각의 전시 주제로 구성되어 있고 조선시대 사랑방이 재형 되어 있다. 중요 작품으로는 영조와 정조가 쓴 어필첩 등이 있다.",
                    lat = "",
                    lng = ""
                ),
                detailIntro = CommonDetail(
                    parking = "가능",
                    time = "상시개방",
                    restDate = "연중무휴"
                ),
                isFavorite = true,
                eventStartDate = "03.23",
                eventEndDate = "12.15",
                contentId = "1111",
                contentTypeId = TYPE_FESTIVAL
            ),
            onClick = {}
        )
    }
}