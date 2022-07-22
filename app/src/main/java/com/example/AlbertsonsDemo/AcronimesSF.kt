package com.example.AlbertsonsDemo

import com.google.gson.annotations.SerializedName

data class AcromineSF(
    @SerializedName("sf")
    val sf: String,
    @SerializedName("lfs")
    val lfs: List<AcromineLF>
)

data class AcromineLF(
    @SerializedName("lf")
    val lf: String,
    @SerializedName("freq")
    val freq: Int,
    @SerializedName("since")
    val since: Int,
    @SerializedName("vars")
    val vars: List<VarsData>
)

data class VarsData(
    @SerializedName("lf")
    val lf: String,
    @SerializedName("freq")
    val freq: Int,
    @SerializedName("since")
    val since: Int
)