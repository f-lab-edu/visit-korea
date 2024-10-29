package kr.ksw.visitkorea.presentation.festival.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.presentation.festival.viewmodel.FestivalActions
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun FestivalFilterDialog(
    areaCodes: List<String>,
    onAction: (FestivalActions) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 480.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                count = areaCodes.size + 1,
                key = { it }
            ) { index ->
                TextButton(
                    onClick = {
                        onAction(FestivalActions.ClickFilterItem(
                            index = index - 1
                        ))
                    },
                    border = BorderStroke(
                        1.dp,
                        Color.Gray
                    )
                ) {
                    Text(
                        text = if(index == 0) {
                            stringResource(R.string.filter_dialog_all)
                        } else {
                            areaCodes[index - 1]
                        },
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FestivalFilterDialogPreview() {
    VisitKoreaTheme {
        Surface {
            FestivalFilterDialog(
                areaCodes = listOf(
                    "서울특별시", "인천광역시", "경기도"
                ),
            ) {

            }
        }
    }
}