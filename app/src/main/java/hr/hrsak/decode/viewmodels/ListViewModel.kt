package hr.hrsak.decode.viewmodels



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.hrsak.decode.api.FIRST_PAGE
import hr.hrsak.decode.api.ITEMS_PER_PAGE
import hr.hrsak.decode.framework.Resource
import hr.hrsak.decode.framework.Sort
import hr.hrsak.decode.models.RepoList
import hr.hrsak.decode.repositories.ReposRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repo: ReposRepository) : ViewModel(){

    sealed class SearchResults {
        class Success (val repos: RepoList) : SearchResults()
        class Failure (val errorText: String) : SearchResults()
        object Loading : SearchResults()
        object Empty : SearchResults()
    }

    private val _listOfRepos = MutableStateFlow<SearchResults>(SearchResults.Empty)
    val listOfRepos : StateFlow<SearchResults> = _listOfRepos

    fun getRepos(query: String, perPage: Int= ITEMS_PER_PAGE, page:Int = FIRST_PAGE, sorting: Sort = Sort.NONE){
        viewModelScope.launch (Dispatchers.IO){
            _listOfRepos.value = SearchResults.Loading
            when(val repos = repo.getItems(query, perPage, page)){
                is Resource.Error -> _listOfRepos.value = SearchResults.Failure(repos.message!!)
                is Resource.Success -> {
                    _listOfRepos.value = SearchResults.Success(repos.data!!)
                    sortRepo(sorting)
                }
            }
        }
    }

     fun sortRepo(sorting: Sort) {
        if (_listOfRepos.value !is SearchResults.Success) return
            when(sorting){
                Sort.NONE -> return
                Sort.FORKS -> (_listOfRepos.value as SearchResults.Success).repos.items = (_listOfRepos.value as SearchResults.Success).repos.items.sortedBy { t -> t.forks }
                Sort.ISSUES -> (_listOfRepos.value as SearchResults.Success).repos.items = (_listOfRepos.value as SearchResults.Success).repos.items.sortedBy { t -> t.issues }
                Sort.WATCHERS -> (_listOfRepos.value as SearchResults.Success).repos.items = (_listOfRepos.value as SearchResults.Success).repos.items.sortedBy { t -> t.watchers }
            }

        }



}