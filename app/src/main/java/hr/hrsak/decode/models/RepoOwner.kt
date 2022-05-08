package hr.hrsak.decode.models

import com.google.gson.annotations.SerializedName

class RepoOwner (
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val picturePath: String,
    @SerializedName("html_url") val htmlUrl: String
        )