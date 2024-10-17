package kr.ksw.visitkorea.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MoreButton(
    onMoreClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onMoreClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "더보기",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "관광지 더보기",
            modifier = Modifier
                .size(20.dp),
            tint = Color.Gray
        )
    }
}