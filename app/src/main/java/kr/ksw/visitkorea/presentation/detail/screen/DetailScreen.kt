package kr.ksw.visitkorea.presentation.detail.screen

import android.os.Build
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.domain.common.TYPE_FESTIVAL
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.domain.usecase.model.CommonDetail
import kr.ksw.visitkorea.presentation.common.convertHtmlToString
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailState
import kr.ksw.visitkorea.presentation.detail.viewmodel.DetailViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun DetailScreen(
    viewModel: DetailViewModel
) {
    val detailState by viewModel.detailState.collectAsState()
    DetailScreen(detailState)
}

@Composable
private fun DetailScreen(
    detailState: DetailState
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
                    colorFilter = if(detailState.firstImage.isNotEmpty())
                        ColorFilter.tint(Color.LightGray, blendMode = BlendMode.Darken)
                    else
                        null,
                    contentDescription = "Detail Image",
                    contentScale = ContentScale.Crop,
                )
                if(detailState.contentTypeId == TYPE_TOURIST_SPOT ||
                    detailState.contentTypeId == TYPE_FESTIVAL) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .size(24.dp),
                        tint = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TitleView(
                detailState.title,
                detailState.address,
                detailState.dist
            )
            Spacer(modifier = Modifier.height(16.dp))
            OverView(detailState.detailCommon.overview)
            Spacer(modifier = Modifier.height(16.dp))
            DetailIntroView(
                contentTypeId = detailState.contentTypeId,
                detailIntro = detailState.detailIntro
            )
            Spacer(modifier = Modifier.height(16.dp))
            ImageViews(detailState.images)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TitleView(
    title: String,
    address: String,
    dist: String?
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleLineText(
                modifier = Modifier
                    .weight(1.0f),
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp),
                text = "지도보기",
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .weight(1.0f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null
                )
                SingleLineText(
                    text = address,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            dist?.run {
                Text(
                    text = this,
                    fontSize = 12.sp
                )
            }
        }
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

@Composable
private fun DetailIntroContent(
    title: String,
    content: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.width(76.dp),
            text = title,
            fontSize = 14.sp
        )
        Text(
            text = content
                .convertHtmlToString()
                .ifEmpty { "문의" },
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ImageViews(
    images: List<DetailImageDTO>
) {
    Text(
        modifier = Modifier
            .padding(start = 16.dp),
        text = "사진",
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            count = images.size,
            key = {
                images[it].originImgUrl
            }
        ) { index ->
            val image = images[index].smallImageUrl
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.LightGray),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(image)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Culture Spot Image",
                contentScale = ContentScale.Crop,
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
                    overview = "수원박물관은 지하 1층, 지상 2층으로 수원역사박물관, 한국서예박물관 등 2개의 상설전시관과 특별전시실로 구성되어 있다. 오감으로 즐기고 배우는 어린이체험실, 다채로운 교육 프로그램 등이 운영되고, 소장유물은 수원지역에서 발굴된 유물, 구입 및 기증받은 유물 등으로 48,000여 점을 소장하고 있다. 수원역사박물관은 수원의 유구한 역사와 문화를 과거·현재·미래의 시점과 주제별로 구성하였으며, 크게 네 부분으로 구성된 전시관은 ‘수원의 자연환경’, ‘선사·역사시대의 변천사’, ‘수원로의 개설’, ‘60년대 수원 만나기’, ‘근대 수원의 문화’로 과거의 역사와 문화뿐만 아니라 현대의 발전하는 역동적인 수원의 모습을 다양하게 보여주고 있다. 한국서예박물관은 지방자치단체에서는 최초로 건립한 서예 전문 박물관으로, 우리나라 서예사를 한눈에 살펴볼 수 있다. 전시는 유물의 시기와 특성에 따라 각각의 전시 주제로 구성되어 있고 조선시대 사랑방이 재형 되어 있다. 중요 작품으로는 영조와 정조가 쓴 어필첩 등이 있다."
                ),
                detailIntro = CommonDetail(
                    parking = "가능",
                    time = "상시개방",
                    restDate = "연중무휴"
                )
            )
        )
    }
}