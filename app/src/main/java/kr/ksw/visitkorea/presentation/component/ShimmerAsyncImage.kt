package kr.ksw.visitkorea.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ShimmerAsyncImage(
    modifier: Modifier,
    data: String,
    colorFilter: ColorFilter? = null,
    contentDescription: String? = null
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    AsyncImage(
        modifier = modifier
            .shimmerEffect(isLoading),
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(data)
            .size(Size.ORIGINAL)
            .build(),
        colorFilter = colorFilter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        onSuccess = { isLoading = false },
        onError = { isLoading = false },
    )
}