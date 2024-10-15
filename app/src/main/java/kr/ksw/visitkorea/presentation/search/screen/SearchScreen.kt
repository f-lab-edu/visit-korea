package kr.ksw.visitkorea.presentation.search.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.collectLatest
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.detail.DetailActivity
import kr.ksw.visitkorea.presentation.component.CommonCard
import kr.ksw.visitkorea.presentation.search.viewmodel.SearchActions
import kr.ksw.visitkorea.presentation.search.viewmodel.SearchState
import kr.ksw.visitkorea.presentation.search.viewmodel.SearchUiEffect
import kr.ksw.visitkorea.presentation.search.viewmodel.SearchViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchState by viewModel.searchState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(viewModel.searchUiEffect) {
        viewModel.searchUiEffect.collectLatest { effect ->
            when(effect) {
                is SearchUiEffect.StartDetailActivity -> {
                    context.startActivity(Intent(
                        context,
                        DetailActivity::class.java
                    ).apply {
                        putExtra("detail", effect.data)
                    })
                }
            }
        }
    }

    SearchScreen(
        searchState,
        viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchState: SearchState,
    onAction: (SearchActions) -> Unit,
) {
    val searchCardModels = searchState.searchCardModelFlow.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "검색",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "키워드로 관광지를 찾아보세요!",
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchState.searchKeyword,
                        onQueryChange = {
                            onAction(SearchActions.UpdateSearchKeyword(it))
                        },
                        onSearch = {
                            onAction(SearchActions.SubmitSearchKeyword)
                            focusManager.clearFocus()
                        },
                        expanded = false,
                        onExpandedChange = {},
                        placeholder = {
                            Text(text = "검색어를 입력해주세요")
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    )
                },
                expanded = false,
                onExpandedChange = {}
            ) {

            }
            Spacer(modifier = Modifier.height(10.dp))

            if(searchState.isLoadingImages) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        count = searchCardModels.itemCount,
                        key = { index ->
                            searchCardModels[index]?.contentId?.toInt() ?: index
                        }
                    ) { index ->
                        val model = searchCardModels[index]
                        model?.run {
                            CommonCard(
                                title = title,
                                address = address,
                                image = firstImage
                            ) {
                                onAction(SearchActions.ClickCardItem(
                                    DetailParcel(
                                        title = title,
                                        firstImage = firstImage,
                                        address = address,
                                        dist = dist,
                                        contentId = contentId,
                                        contentTypeId = contentTypeId
                                    )
                                ))
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
fun SearchScreenPreview() {
    VisitKoreaTheme {
        SearchScreen(
            searchState = SearchState(),
        ) {

        }
    }
}

