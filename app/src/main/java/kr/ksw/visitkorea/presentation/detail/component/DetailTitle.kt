package kr.ksw.visitkorea.presentation.detail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ksw.visitkorea.presentation.common.convertHtmlToString
import kr.ksw.visitkorea.presentation.component.SingleLineText
import kr.ksw.visitkorea.presentation.ui.theme.VisitKoreaTheme

@Composable
fun DetailTitleView(
    title: String,
    address: String,
    dist: String?,
    homePage: String = "",
    tel: String = "",
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleLineText(
                modifier = Modifier
                    .weight(1.0f),
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp),
                text = "지도보기",
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .weight(1.0f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null
                )
                SingleLineText(
                    text = address,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            dist?.run {
                Text(
                    text = this,
                    fontSize = 12.sp
                )
            }
        }
        if(homePage.isNotEmpty() || tel.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            if(homePage.isNotEmpty()) {
                SubInfoView(
                    icon = Icons.Outlined.Info,
                    content = homePage.convertHtmlToString()
                )
            }
            if(tel.isNotEmpty()) {
                SubInfoView(
                    icon = Icons.Outlined.Phone,
                    content = tel
                )
            }
        }
    }
}

@Composable
private fun SubInfoView(
    icon: ImageVector,
    content: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = content
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TitlePreview() {
    VisitKoreaTheme {
        Surface {
            DetailTitleView(
                title = "숙박업체 이름",
                address = "숙박업체 주소",
                dist = "600m",
                homePage = "https://www.hotel.com",
                tel = "02-0000-0000"
            )
        }
    }
}