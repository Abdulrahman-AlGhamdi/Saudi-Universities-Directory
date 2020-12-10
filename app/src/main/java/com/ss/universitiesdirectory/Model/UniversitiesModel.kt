package com.ss.universitiesdirectory.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UniversitiesModel(
        var Name: String = "",
        var Logo: String = "",
        var News: String = "",
        var About: String = "",
        var Youtube: String = "",
        var Twitter: String = "",
        var College: String = "",
        var Website: String = "",
        var Snapchat: String = "",
        var Location: String = "",
        var Facebook: String = "",
        var Instagram: String = "",
        var Application: String = ""
) : Parcelable