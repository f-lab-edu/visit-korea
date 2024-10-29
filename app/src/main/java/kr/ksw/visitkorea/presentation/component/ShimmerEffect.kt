package kr.ksw.visitkorea.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShimmerEffect(): Brush {
    val shimmerColor = listOf(
        Color.LightGray.copy(alpha = 0.8f),
        Color.LightGray.copy(alpha = 0.4f),
        Color.LightGray.copy(alpha = 0.8f)
    )
    val transition = rememberInfiniteTransition(label = "Shimmer Transition")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 550f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = "Shimmer Animation"
    )
    return Brush.linearGradient(
        colors = shimmerColor,
        start = Offset.Zero,
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
}