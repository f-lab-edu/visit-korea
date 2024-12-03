package kr.ksw.visitkorea.presentation.common

import android.location.Location

const val DEFAULT_LATITUDE = "37.5678958128"
const val DEFAULT_LONGITUDE = "126.9817290217"

fun Location.latitudeToStringOrDefault(): String = if(latitude == 0.0)
    DEFAULT_LATITUDE
else
    latitude.toString()

fun Location.longitudeToStringOrDefault(): String = if(longitude == 0.0)
    DEFAULT_LONGITUDE
else
    longitude.toString()
