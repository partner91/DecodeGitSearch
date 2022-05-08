package hr.hrsak.decode.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hr.hrsak.decode.R
import hr.hrsak.decode.adapters.ReposAdapter
import hr.hrsak.decode.databinding.FragmentRepositoriesBinding
import hr.hrsak.decode.framework.Sort
import hr.hrsak.decode.models.RepoItem
import hr.hrsak.decode.view.MainActivity
import hr.hrsak.decode.viewmodels.ListViewModel
import kotlinx.android.synthetic.main.fragment_repositories.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {

    private var sorted : Sort = Sort.NONE
    private lateinit var  viewModel : ListViewModel
    private lateinit var repoAdapter : ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = ProgressBar.INVISIBLE
        repoAdapter = ReposAdapter(mutableListOf(), requireActivity())
        recycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = repoAdapter
        }
        updateAdapter()
    }

    private fun updateAdapter() {
        var repos : MutableList<RepoItem> = mutableListOf()
        lifecycleScope.launchWhenStarted {
            viewModel.listOfRepos.collect{ event ->
                when(event){
                    is ListViewModel.SearchResults.Success ->{
                        progressBar.isVisible = false
                        repos = event.repos.items.toMutableList()
                        repoAdapter.update(repos)
                    }
                    is ListViewModel.SearchResults.Failure -> {
                        progressBar.isVisible = false
                        tvErrorText.text = event.errorText
                    }
                    is ListViewModel.SearchResults.Loading ->{
                        progressBar.isVisible = true

                    }
                    else -> Unit
                }}
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu,menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getRepos(p0 ?: "", sorting = sorted)
                when(sorted){
                    Sort.NONE -> sortByOptions(null,sorted)
                    Sort.FORKS -> sortByOptions(null,sorted)
                    Sort.ISSUES -> sortByOptions(null,sorted)
                    Sort.WATCHERS -> sortByOptions(null,sorted)
                }
                updateAdapter()
                return true;
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })

        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.sortNone ->{
                sortByOptions(item,Sort.NONE)
            }
            R.id.sortForks ->{
                sortByOptions(item,Sort.FORKS)

            }
            R.id.sortIssues -> {
                sortByOptions(item,Sort.ISSUES)
            }
            R.id.sortWatchers -> {
                sortByOptions(item,Sort.WATCHERS)
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun sortByOptions(item: MenuItem? ,sort: Sort): Boolean{
        item?.isChecked =  !item!!.isChecked
        sorted = sort
        viewModel.sortRepo(sort)
        updateAdapter()
        return true
    }

}