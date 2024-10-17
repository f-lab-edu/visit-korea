package kr.ksw.visitkorea.presentation.more.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.domain.model.MoreCardModel
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.more.viewmodel.MoreActions
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun MoreTouristCard(
    model: MoreCardModel,
    onIconClick: (MoreActions) -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(0.7f)
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(6.dp)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(model.firstImage)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Tourist Card",
                contentScale = ContentScale.Crop,
            )
            Icon(
                if(model.isFavorite)
                    Icons.Default.Favorite
                else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(24.dp)
                    .clickable {
                        if(model.isFavorite) {
                            onIconClick(MoreActions.ClickFavoriteIconDelete(
                                model.contentId
                            ))
                        } else {
                            onIconClick(
                                MoreActions.ClickFavoriteIconUpsert(
                                    favorite = FavoriteEntity(
                                        title = model.title,
                                        address = model.address,
                                        dist = model.dist,
                                        firstImage = model.firstImage,
                                        contentId = model.contentId,
                                        contentTypeId = TYPE_TOURIST_SPOT,
                                        eventStartDate = null,
                                        eventEndDate = null,
                                    ),
                                )
                            )
                        }
                    },
                tint = Color.Red
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(vertical = 6.dp, horizontal = 4.dp)
            ) {
                SingleLineText(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = model.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(18.dp),
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                    )
                    SingleLineText(
                        modifier = Modifier.weight(1f),
                        text = model.address,
                        fontSize = 12.sp,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MoreTouristCardPreview() {
    VisitKoreaTheme {
        Surface {
            MoreTouristCard(
                model = MoreCardModel(
                    title = "반달공원",
                    address = "경기도 수원시 영통구 영통1동 1012-4",
                    firstImage = "https://www.images.com",
                    dist = "600m",
                    contentId = "1111",
                    category = null,
                ),
                onIconClick = {},
                onItemClick = {},
            )
        }
    }
}