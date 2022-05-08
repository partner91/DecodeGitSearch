package hr.hrsak.decode.models

import com.google.gson.annotations.SerializedName

class RepoItem (
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("owner") val owner: RepoOwner,
    @SerializedName("html_url")val repoUrl: String,
    @SerializedName("description")val description: String,
    @SerializedName("created_at")val createdAt: String,
    @SerializedName("updated_at")val updatedAt: String,
    @SerializedName("watchers")val watchers: Int,
    @SerializedName("language")val language: String,
    @SerializedName("forks")val forks: Int,
    @SerializedName("open_issues")val issues: Int
    )