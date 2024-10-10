package kr.ksw.visitkorea.presentation.common

import android.location.Location

fun Location.latitudeToStringOrDefault(): String = if(latitude == 0.0)
    "37.5678958128"
else
    latitude.toString()

fun Location.longitudeToStringOrDefault(): String = if(longitude == 0.0)
    "126.9817290217"
else
    longitude.toString()