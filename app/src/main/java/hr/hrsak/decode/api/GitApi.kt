package hr.hrsak.decode.api

import hr.hrsak.decode.models.RepoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.github.com/search/"
const val ITEMS_PER_PAGE = 100
const val FIRST_PAGE = 1

interface GitApi {
    @GET("repositories")
    suspend fun getRepos(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = ITEMS_PER_PAGE,
        @Query("page") page: Int = FIRST_PAGE
    ) : Response<RepoList>
}