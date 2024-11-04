package kr.ksw.visitkorea.presentation.festival.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.collectLatest
import kr.ksw.visitkorea.domain.common.TYPE_FESTIVAL
import kr.ksw.visitkorea.domain.usecase.model.Festival
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.detail.DetailActivity
import kr.ksw.visitkorea.presentation.festival.component.FestivalCard
import kr.ksw.visitkorea.presentation.festival.viewmodel.FestivalActions
import kr.ksw.visitkorea.presentation.festival.viewmodel.FestivalUiEffect
import kr.ksw.visitkorea.presentation.festival.viewmodel.FestivalViewModel
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun FestivalScreen(
    viewModel: FestivalViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val hotelState by viewModel.festivalState.collectAsState()
    val lazyItem = hotelState.festivalModelFlow.collectAsLazyPagingItems()
    LaunchedEffect(viewModel.uiEffect) {
        viewModel.uiEffect.collectLatest { effect ->
            when(effect) {
                is FestivalUiEffect.StartDetailActivity -> {
                    context.startActivity(
                        Intent(
                        context,
                        DetailActivity::class.java
                    ).apply {
                        putExtra("detail", effect.data)
                    })
                }
            }
        }
    }

    FestivalScreen(
        lazyItem,
        viewModel::onAction
    )
}

@Composable
fun FestivalScreen(
    festivals: LazyPagingItems<Festival>,
    onItemClick: (FestivalActions) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 10.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "축제",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    Icons.Outlined.LocationOn,
                    modifier = Modifier
                        .size(32.dp),
                    contentDescription = "Location Filter Icon"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "진행중인 축제를 찾아보세요!",
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = festivals.itemCount,
                    key = { index ->
                        festivals[index]?.contentId?.toInt() ?: index
                    }
                ) { index ->
                    val festival = festivals[index]
                    festival?.run {
                        val model = this
                        FestivalCard(model) {
                            onItemClick(FestivalActions.ClickFestivalCardItem(
                                DetailParcel(
                                    title = festival.title,
                                    address = festival.address,
                                    firstImage = festival.firstImage,
                                    contentId = festival.contentId,
                                    contentTypeId = TYPE_FESTIVAL,
                                    eventStartDate = festival.eventStartDate,
                                    eventEndDate = festival.eventEndDate,
                                )
                            ))
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FestivalPreview() {
    VisitKoreaTheme {
        FestivalScreen()
    }
}