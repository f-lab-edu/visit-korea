package kr.ksw.visitkorea.presentation.festival.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.common.TYPE_FESTIVAL
import kr.ksw.visitkorea.domain.model.Festival
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.festival.viewmodel.FestivalActions
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun FestivalCard(
    festival: Festival,
    onIconClick: (FestivalActions) -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .clip(RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp
                    ))
                    .background(color = Color.LightGray),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(festival.firstImage)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .border(
                        2.dp,
                        Color.DarkGray,
                        RoundedCornerShape(
                            topStart = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        RoundedCornerShape(
                            topStart = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
                    .padding(
                        vertical = 12.dp,
                        horizontal = 10.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = festival.eventStartDate,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.White,
                    letterSpacing = (-0.6).sp
                )
                Text(
                    text = "~",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.White,
                )
                Text(
                    text = festival.eventEndDate,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.White,
                    letterSpacing = (-0.6).sp
                )
            }
            Icon(
                if(festival.isFavorite)
                    Icons.Default.Favorite
                else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(24.dp)
                    .clickable {
                        onIconClick(FestivalActions.ClickFavoriteIcon(
                            entity = FavoriteEntity(
                                title = festival.title,
                                address = festival.address,
                                dist = null,
                                firstImage = festival.firstImage,
                                contentId = festival.contentId,
                                contentTypeId = TYPE_FESTIVAL,
                                eventStartDate = festival.eventStartDate,
                                eventEndDate = festival.eventEndDate
                            ),
                            isFavorite = festival.isFavorite
                        ))
                    },
                tint = Color.Red
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 8.dp)
        ) {
            SingleLineText(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                    ),
                text = festival.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
            Row(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                )
                SingleLineText(
                    text = festival.address,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FestivalCardPreview() {
    VisitKoreaTheme {
        Surface {
            FestivalCard(
                festival = Festival(
                    "경상북도 칠곡군 동명면 남원리",
                    "",
                    "가산산성 문화유산 야행",
                    "1111",
                    "10.11",
                    "10.30"
                ),
                onIconClick = {},
                onItemClick = {}
            )
        }
    }
}