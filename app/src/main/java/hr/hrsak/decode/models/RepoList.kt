package hr.hrsak.decode.models

import com.google.gson.annotations.SerializedName

data class RepoList (
    @SerializedName("total_count") val total_count : Int,
    @SerializedName("incomplete_results") val incomplete_results : Boolean,
    @SerializedName("items") var items : List<RepoItem>
)