package hr.hrsak.decode.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import hr.hrsak.decode.R
import hr.hrsak.decode.models.RepoItem
import hr.hrsak.decode.view.MainActivity
import kotlinx.android.synthetic.main.fragment_repo_detail.*


class RepoDetailFragment(val repoItem: RepoItem) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvRepoTitle.text = repoItem.name
        tvAuthor.text =repoItem.owner.name
        tvDateCreated.text = repoItem.createdAt.trim()
        tvDateUpdated.text = repoItem.updatedAt
        tvForks.text = repoItem.forks.toString()
        tvWatchers.text =repoItem.watchers.toString()
        tvIssues.text = repoItem.issues.toString()
        tvLang.text = repoItem.language
        tvDescription.text = repoItem.description
        tvRepoUrl.text = repoItem.repoUrl
        Picasso.get().load(repoItem.owner.picturePath).into(ivAuthor)
        setListeners()
    }

    private fun setListeners() {
        tvRepoUrl.setOnClickListener{
            val webIntent: Intent = Uri.parse(repoItem.repoUrl).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent)
        }
    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).supportActionBar?.show()
        super.onDestroy()
    }

}