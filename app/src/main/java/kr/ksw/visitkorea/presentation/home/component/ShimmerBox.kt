package kr.ksw.visitkorea.presentation.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.ksw.visitkorea.presentation.component.shimmerEffect

@Composable
fun ShimmerBox(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .shimmerEffect(true)
    )
}