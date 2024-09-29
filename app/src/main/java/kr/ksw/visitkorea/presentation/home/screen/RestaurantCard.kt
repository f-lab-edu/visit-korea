package kr.ksw.visitkorea.presentation.home.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun RestaurantCard(
    title: String,
    address: String,
    dist: String,
    image: String,
) {
    Card(
        modifier = Modifier
            .width(300.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color.LightGray),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(image)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Culture Spot Image",
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                SingleLineText(
                    text = title,
                    fontSize = 16.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                    )
                    SingleLineText(
                        text = address,
                        fontSize = 14.sp
                    )
                }
                SingleLineText(
                    text = dist,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RestaurantCardPreview() {
    VisitKoreaTheme {
        Surface {
            RestaurantCard(
                "음식점", "음식점 주소", "188m", "https"
            )
        }
    }
}