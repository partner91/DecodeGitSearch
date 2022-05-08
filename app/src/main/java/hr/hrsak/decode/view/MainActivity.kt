package hr.hrsak.decode.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.*
import dagger.hilt.android.AndroidEntryPoint
import hr.hrsak.decode.R
import hr.hrsak.decode.databinding.ActivityMainBinding
import hr.hrsak.decode.fragments.RepositoriesFragment
import hr.hrsak.decode.framework.Sort
import hr.hrsak.decode.viewmodels.ListViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, RepositoriesFragment())
            .commit()
    }








}