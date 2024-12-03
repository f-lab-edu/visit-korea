package kr.ksw.visitkorea.presentation.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.presentation.component.ShimmerAsyncImage

@Composable
fun DetailImageRow(
    images: List<DetailImageDTO>,
    onImageClick: (Int) -> Unit
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
                images[it].smallImageUrl
            }
        ) { index ->
            val image = images[index].smallImageUrl
            ShimmerAsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onImageClick(index)
                    },
                data = image,
                contentDescription = "Detail Thumbnail",
            )
        }
    }
}