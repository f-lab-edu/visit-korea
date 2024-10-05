package kr.ksw.visitkorea.presentation.search.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kr.ksw.visitkorea.presentation.home.component.CultureCard
import kr.ksw.visitkorea.presentation.more.component.MoreScreenHeader
import kr.ksw.visitkorea.presentation.more.viewmodel.SearchActions
import kr.ksw.visitkorea.presentation.search.viewmodel.SearchState
import kr.ksw.visitkorea.presentation.search.viewmodel.SearchViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchState by viewModel.searchState.collectAsState()
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
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchState.searchKeyword,
                        onQueryChange = {
                            onAction(SearchActions.UpdateSearchKeyword(it))
                        },
                        onSearch = {
                            onAction(SearchActions.SubmitSearchKeyword)
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
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
                        CultureCard(
                            modifier = Modifier.clickable {
                                // Go to DetailActivity
                            },
                            title = title,
                            address = address,
                            image = firstImage
                        )
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

