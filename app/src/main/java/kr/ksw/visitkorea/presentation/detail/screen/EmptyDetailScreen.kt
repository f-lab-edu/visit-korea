package kr.ksw.visitkorea.presentation.detail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun EmptyDetailScreen() {
    Surface {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_error_96),
                contentDescription = "Error Screen"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.empty_screen_waring_text),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
@Preview
fun EmptyDetailScreenPreview() {
    VisitKoreaTheme {
        EmptyDetailScreen()
    }
}