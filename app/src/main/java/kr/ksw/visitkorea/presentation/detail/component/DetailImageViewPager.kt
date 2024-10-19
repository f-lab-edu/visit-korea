package kr.ksw.visitkorea.presentation.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun DetailImageViewPager(
    selectedImage: Int,
    images: List<String>,
) {
    val pagerState = rememberPagerState(
        initialPage = selectedImage,
        pageCount = { images.size }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.9f)
            ),
    ) {
        HorizontalPager(
            modifier = Modifier
                .align(Alignment.Center),
            key = { index ->
                images[index]
            },
            state = pagerState
        ) { page ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .background(color = Color.LightGray),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(images[page])
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Culture Spot Image",
                contentScale = ContentScale.Crop,
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter),
        ) {
            Text(
                text = "${pagerState.currentPage+1}/${images.size}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.1f)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DetailImageViewPagerPreview() {
    VisitKoreaTheme {
        Surface {
            DetailImageViewPager(
                selectedImage = 0,
                images = listOf("")
            )
        }
    }
}