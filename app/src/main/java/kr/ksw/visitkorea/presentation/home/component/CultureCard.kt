package kr.ksw.visitkorea.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun CultureCard(
    modifier: Modifier = Modifier,
    ratio: Float = 0.7f,
    title: String,
    address: String,
    image: String,
) {
    Card (
        modifier = modifier
            .aspectRatio(ratio),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
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
fun CultureCardPreview() {
    VisitKoreaTheme {
        Surface(
            modifier = Modifier
                .padding(20.dp)
        ) {
            CultureCard(
                title = "문화시설",
                address = "문화시설 주소",
                image = "https://ksw"
            )
        }
    }
}