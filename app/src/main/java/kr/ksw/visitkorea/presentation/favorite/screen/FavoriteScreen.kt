package kr.ksw.visitkorea.presentation.favorite.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.ksw.visitkorea.presentation.favorite.viewmodel.FavoriteState
import kr.ksw.visitkorea.presentation.favorite.viewmodel.FavoriteViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteState by viewModel.favoriteState.collectAsState()
    Log.d("FavoriteScreen", "${favoriteState.favoriteList}")
    FavoriteScreen(favoriteState)
}

@Composable
fun FavoriteScreen(
    state: FavoriteState
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 10.dp
                    ),
                text = "즐겨찾기",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyColumn(

            ) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritePreview() {
    VisitKoreaTheme {
        FavoriteScreen()
    }
}