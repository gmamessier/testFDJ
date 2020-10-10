package com.example.testfdj.search.model

import com.google.gson.annotations.SerializedName

data class Team (
    @SerializedName("idTeam")
    val id: Int,
    @SerializedName("strTeam")
    val name: String,
    @SerializedName("strTeamBadge")
    val logo: String,
    @SerializedName("strTeamBanner")
    val banner: String,
    @SerializedName("strDescriptionEN")
    val description: String,
    @SerializedName("strCountry")
    val country: String,
    @SerializedName("strLeague")
    val league: String
)