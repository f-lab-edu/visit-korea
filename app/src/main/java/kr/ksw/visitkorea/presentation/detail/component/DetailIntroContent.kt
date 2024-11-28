package kr.ksw.visitkorea.presentation.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ksw.visitkorea.presentation.common.convertHtmlToString

@Composable
fun DetailIntroContent(
    title: String,
    content: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.width(76.dp),
            text = title,
            fontSize = 14.sp
        )
        Text(
            text = content
                .convertHtmlToString()
                .ifEmpty { "문의" },
            fontSize = 14.sp
        )
    }
}