package kr.ksw.visitkorea.presentation.favorite.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kr.ksw.visitkorea.domain.model.Favorite
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.component.CommonCard
import kr.ksw.visitkorea.presentation.detail.DetailActivity
import kr.ksw.visitkorea.presentation.favorite.viewmodel.FavoriteActions
import kr.ksw.visitkorea.presentation.favorite.viewmodel.FavoriteUiEffect
import kr.ksw.visitkorea.presentation.favorite.viewmodel.FavoriteViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteState by viewModel.favoriteState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(viewModel.favoriteUiEffect) {
        viewModel.favoriteUiEffect.collectLatest { effect ->
            when(effect) {
                is FavoriteUiEffect.StartDetailActivity -> {
                    context.startActivity(
                        Intent(
                            context,
                            DetailActivity::class.java
                        ).apply {
                            putExtra("detail", effect.data)
                        }
                    )
                }
            }
        }
    }

    FavoriteScreen(
        favoriteList = favoriteState.favoriteList,
        onItemClick = viewModel::onAction
    )
}

@Composable
fun FavoriteScreen(
    favoriteList: List<Favorite>,
    onItemClick: (FavoriteActions) -> Unit
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    count = favoriteList.size,
                    key = { index ->
                        favoriteList[index].contentId
                    }
                ) { index ->
                    val favorite = favoriteList[index]
                    favorite.run {
                        CommonCard(
                            title = title,
                            address = address,
                            image = firstImage,
                            contentTypeId = contentTypeId,
                            favorite = true,
                            onIconClick = {
                                onItemClick(
                                    FavoriteActions.ClickFavoriteIcon(this)
                                )
                            },
                        ) {
                            onItemClick(
                                FavoriteActions.ClickCardItem(
                                    DetailParcel(
                                        title = title,
                                        address = address,
                                        dist = dist,
                                        firstImage = firstImage,
                                        contentId = contentId,
                                        contentTypeId = contentTypeId
                                    )
                                )
                            )
                        }
                    }
                }
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