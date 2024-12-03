package kr.ksw.visitkorea.presentation.common

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import kr.ksw.visitkorea.BuildConfig
import kr.ksw.visitkorea.R

fun Context.openMap(
    lat: String,
    lng: String,
    name: String
) {
    val url = getString(
        R.string.open_naver_map_url_scheme,
        lat,
        lng,
        name,
        BuildConfig.APPLICATION_ID
    )
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addCategory(Intent.CATEGORY_BROWSABLE)

    val list: List<ResolveInfo> = packageManager.queryIntentActivities(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY
    )
    if (list.isEmpty()) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.open_naver_map_market))
            )
        )
    } else {
        startActivity(intent)
    }
}