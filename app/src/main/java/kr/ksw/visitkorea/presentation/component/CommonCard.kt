package kr.ksw.visitkorea.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ksw.visitkorea.domain.common.TYPE_FESTIVAL
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun CommonCard(
    modifier: Modifier = Modifier,
    title: String,
    address: String,
    image: String,
    contentTypeId: String,
    favorite: Boolean = false,
    onIconClick: () -> Unit = {},
    onItemClick: () -> Unit
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    Card (
        modifier = modifier
            .aspectRatio(0.7f)
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            Box {
                ShimmerAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    data = image,
                    contentDescription = "Culture Spot Image"
                )
                if(contentTypeId == TYPE_TOURIST_SPOT ||
                    contentTypeId == TYPE_FESTIVAL) {
                    Icon(
                        if(favorite)
                            Icons.Default.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .size(24.dp)
                            .clickable(onClick = onIconClick),
                        tint = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                SingleLineText(
                    modifier = Modifier.padding(start = 2.dp),
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
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
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CommonCardPreview() {
    VisitKoreaTheme {
        Surface(
            modifier = Modifier
                .padding(20.dp)
        ) {
            CommonCard(
                title = "문화시설",
                address = "문화시설 주소",
                image = "https://ksw",
                contentTypeId = TYPE_TOURIST_SPOT,
                favorite = true
            ) {

            }
        }
    }
}