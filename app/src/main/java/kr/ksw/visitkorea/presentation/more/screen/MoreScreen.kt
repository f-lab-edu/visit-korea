package kr.ksw.visitkorea.presentation.more.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.usecase.model.MoreCardModel
import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.home.component.CultureCard
import kr.ksw.visitkorea.presentation.home.component.RestaurantCard
import kr.ksw.visitkorea.presentation.more.component.MoreScreenHeader
import kr.ksw.visitkorea.presentation.more.component.MoreTouristCard
import kr.ksw.visitkorea.presentation.more.viewmodel.MoreViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    viewModel: MoreViewModel,
    contentType: ContentType,
    onBackButtonClick: () -> Unit
) {
    val moreState by viewModel.moreState.collectAsState()
    val moreCardModels = moreState.moreCardModelFlow.collectAsLazyPagingItems()
    val state = rememberPullToRefreshState()

    val onRefresh = {
        viewModel.getMoreListByContentType(contentType.contentTypeId, true)
    }

    MoreScreen(
        state,
        moreState.isRefreshing,
        onRefresh,
        moreCardModels = moreCardModels,
        contentType,
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
    contentType: ContentType,
    onBackButtonClick: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MoreScreenHeader(
                title = stringResource(contentType.title),
                onBackButtonClick = onBackButtonClick
            )
            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize(),
                state = state,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
            ) {
                if(contentType == ContentType.RESTAURANT) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        items(
                            count = moreCardModels.itemCount,
                            key = { index ->
                                moreCardModels[index]?.contentId?.toInt() ?: index
                            }
                        ) { index ->
                            val model = moreCardModels[index]
                            model?.run {
                                RestaurantCard(
                                    title = title,
                                    address = address,
                                    dist = dist,
                                    category = category ?: "",
                                    image = firstImage,
                                    modifier = Modifier.fillMaxWidth()
                                ) { }
                            }
                        }
                    }
                } else {
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
                            val model = moreCardModels[index]
                            model?.run {
                                when(contentType) {
                                    ContentType.TOURIST -> MoreTouristCard(
                                        title = title,
                                        address = address,
                                        image = firstImage
                                    )
                                    else -> CultureCard(
                                        title = title,
                                        address = address,
                                        image = firstImage
                                    ) {

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MoreScreenPreview() {
    VisitKoreaTheme {
        MoreScreen(
            viewModel = hiltViewModel(),
            contentType = ContentType.TOURIST
        ) { }
    }
}