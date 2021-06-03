package com.ss.universitiesdirectory.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniversityModel(
    var Name: String = "",
    var Logo: String = "",
    var News: String = "",
    var About: String = "",
    var Youtube: String = "",
    var Twitter: String = "",
    var Colleges: String = "",
    var Website: String = "",
    var Snapchat: String = "",
    var Location: String = "",
    var Facebook: String = "",
    var Instagram: String = "",
    var Application: String = "",
    var Province: Boolean = false,
) : Parcelable