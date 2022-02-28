package com.ss.universitiesdirectory.data.model.univeristy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniversityModel(
    val id          : Int = 0,
    val name        : String = "",
    val about       : String = "",
    val logo        : String = "",
    val colleges    : String = "",
    val location    : String = "",
    val website     : String = "",
    val application : String = "",
    val news        : String = "",
    val region      : String = "",
    val snapchat    : String = "",
    val instagram   : String = "",
    val twitter     : String = "",
    val youtube     : String = "",
    val facebook    : String = "",
    val province    : Boolean = false
) : Parcelable