package com.example.testfdj.search.model

import com.google.gson.annotations.SerializedName

data class League (
    @SerializedName("idLeague")
    val id: Int,
    @SerializedName("strLeague")
    val name: String,
    @SerializedName("strSport")
    val sport: String,
    @SerializedName("strLeagueAlternate")
    val alternateName: String?
)