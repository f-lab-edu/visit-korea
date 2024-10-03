package kr.ksw.visitkorea.presentation.more.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.domain.usecase.model.MoreCardModel
import kr.ksw.visitkorea.presentation.home.component.CultureCard
import kr.ksw.visitkorea.presentation.more.component.MoreScreenHeader
import kr.ksw.visitkorea.presentation.more.component.MoreTouristCard
import kr.ksw.visitkorea.presentation.more.viewmodel.MoreViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    viewModel: MoreViewModel,
    contentTypeId: String,
    onBackButtonClick: () -> Unit
) {
    val moreState by viewModel.moreState.collectAsState()
    val moreCardModels = moreState.moreCardModelFlow.collectAsLazyPagingItems()
    val state = rememberPullToRefreshState()

    val onRefresh = {
        viewModel.getMoreListByContentType(contentTypeId, true)
    }

    MoreScreen(
        state,
        moreState.isRefreshing,
        onRefresh,
        moreCardModels = moreCardModels,
        contentTypeId,
        onBackButtonClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    moreCardModels: LazyPagingItems<MoreCardModel>,
    contentTypeId: String,
    onBackButtonClick: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MoreScreenHeader(
                title = stringResource(moreTitle(contentTypeId)),
                onBackButtonClick = onBackButtonClick
            )
            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize(),
                state = state,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        count = moreCardModels.itemCount,
                        key = { index ->
                            moreCardModels[index]?.contentId?.toInt() ?: index
                        }
                    ) { index ->
                        val hotel = moreCardModels[index]
                        hotel?.run {
                            MoreTouristCard(
                                title = hotel.title,
                                address = hotel.address,
                                image = hotel.firstImage
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun moreTitle(contentTypeId: String) = when(contentTypeId) {
    "12" -> R.string.tourist_spot_title
    "14" -> R.string.culture_center_title
    "28" -> R.string.leisure_sports_title
    else -> R.string.restaurant_title
}

@Composable
@Preview(showBackground = true)
fun MoreScreenPreview() {
    VisitKoreaTheme {
        MoreScreen(
            viewModel = hiltViewModel(),
            contentTypeId = "12"
        ) { }
    }
}