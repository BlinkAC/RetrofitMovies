package com.example.recyclerview.utils

import com.google.gson.annotations.SerializedName
import java.util.*

data class ProvidersResponse(var results: Results)

data class Results (

    @SerializedName("MX" ) var MX : MX? = MX()
)

data class MX (

    @SerializedName("link"     ) var link     : String?        = null,
    @SerializedName("flatrate" ) var flatrate : List<Flatrate> = arrayListOf()

)

data class Flatrate (

    @SerializedName("display_priority" ) var displayPriority : Int?    = null,
    @SerializedName("logo_path"        ) var logoPath        : String? = null,
    @SerializedName("provider_id"      ) var providerId      : Int?    = null,
    @SerializedName("provider_name"    ) var providerName    : String? = null

)