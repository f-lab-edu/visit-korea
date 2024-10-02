package kr.ksw.visitkorea.presentation.more.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun MoreScreenHeader(
    title: String,
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp
            ),
    ) {
        IconButton(
            onClick = onBackButtonClick
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back Button",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterStart)
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )
    }
}

@Composable
@Preview
fun MoreScreenHeaderPreview() {
    VisitKoreaTheme {
        Surface {
            MoreScreenHeader(
                "관광지"
            ) {

            }
        }
    }
}