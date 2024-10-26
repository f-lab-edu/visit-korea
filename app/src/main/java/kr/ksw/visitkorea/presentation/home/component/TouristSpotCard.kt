package kr.ksw.visitkorea.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun TouristSpotCard(
    title: String,
    address: String,
    dist: String,
    image: String,
    onItemClick: () -> Unit
) {
    Card (
        modifier = Modifier
            .width(200.dp)
            .aspectRatio(0.75f)
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(image)
                    .size(Size.ORIGINAL)
                    .build(),
                colorFilter = if(image.isNotEmpty())
                    ColorFilter.tint(Color.LightGray, blendMode = BlendMode.Darken)
                else
                    null,
                contentDescription = "TouristSpot Image",
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SingleLineText(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                )
                Row {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Color.White
                    )
                    SingleLineText(
                        modifier = Modifier.weight(1f),
                        text = address,
                        fontSize = 14.sp,
                        color = Color.White,
                    )
                    SingleLineText(
                        text = dist,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TouristSpotCardPreview() {
    VisitKoreaTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TouristSpotCard(
                "광통교", "서울 특별시 종로구 서린동", "16m", ""
            ) {
                
            }
        }
    }
}