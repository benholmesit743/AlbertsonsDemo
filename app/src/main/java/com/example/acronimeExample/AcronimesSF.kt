package com.example.acronimeExample

import com.google.gson.annotations.SerializedName

data class AcronimesSF(
    @SerializedName("sf")
    val sf: String,
    @SerializedName("lfs")
    val lfs: List<AcronimesLF>
)

data class AcronimesLF(
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