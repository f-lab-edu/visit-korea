package kr.ksw.visitkorea.presentation.common

import android.text.Html

fun String.convertHtmlToString(): String = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()