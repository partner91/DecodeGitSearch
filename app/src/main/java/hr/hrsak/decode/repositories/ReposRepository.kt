package hr.hrsak.decode.repositories


import hr.hrsak.decode.api.FIRST_PAGE
import hr.hrsak.decode.api.GitApi
import hr.hrsak.decode.api.ITEMS_PER_PAGE
import hr.hrsak.decode.framework.Resource
import hr.hrsak.decode.models.RepoList
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ReposRepository @Inject constructor(private val api: GitApi) {

    suspend fun getItems(query:String, perPage: Int = ITEMS_PER_PAGE, page: Int = FIRST_PAGE): Resource<RepoList>{
        return try {
            val response = api.getRepos(query,perPage,page)
            val result = response.body()
            if(response.isSuccessful && result != null)
                Resource.Success(result)
            else
                Resource.Error(response.message())
        }catch (e: Exception){
            return  Resource.Error(e.message ?: "An error occurred")
        }
    }
}