package hr.hrsak.decode.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ReportFragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.hrsak.decode.R
import hr.hrsak.decode.fragments.AuthorFragment
import hr.hrsak.decode.fragments.RepoDetailFragment
import hr.hrsak.decode.models.RepoItem
import hr.hrsak.decode.view.MainActivity
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.repo_sumamry.view.*


class ReposAdapter(private var repos: MutableList<RepoItem>, private val context: Context)
    :RecyclerView.Adapter<ReposAdapter.ViewHolder>(){

    fun update(newRepo: MutableList<RepoItem>){
        repos = newRepo
        notifyDataSetChanged()
    }

        class ViewHolder(repoView: View): RecyclerView.ViewHolder(repoView){
            private val ivAuthor: ImageView = itemView.findViewById(R.id.ivAuthor)
            private val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
            private val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
            private val tvForks: TextView = itemView.findViewById(R.id.tvForks)
            private val tvIssues: TextView = itemView.findViewById(R.id.tvIssues)
            private val tvWatchers: TextView = itemView.findViewById(R.id.tvWatchers)

            fun bind(repo: RepoItem){
                Picasso.get()
                    .load(repo.owner.picturePath)
                    .error(R.drawable.ic_issues_black)
                    .transform(RoundedCornersTransformation(50,5))
                    .into(ivAuthor)
                tvTitle.text = repo.name
                tvAuthor.text = repo.owner.name
                tvForks.text = repo.forks.toString()
                tvIssues.text = repo.issues.toString()
                tvWatchers.text = repo.watchers.toString()
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val repoView = LayoutInflater.from(parent.context).inflate(
            R.layout.repo_sumamry, parent, false
        )
        return ViewHolder(repoView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.ivAuthor.setOnClickListener{
            openAuthorDetails(holder, position)
        }

        holder.itemView.tvAuthor.setOnClickListener{
            openAuthorDetails(holder, position)
        }

        holder.itemView.tvTitle.setOnClickListener{
            openRepoDetails(holder,position)
        }
        holder.bind(repos[position])
    }

    override fun getItemCount() = repos.size


    private fun openRepoDetails(holder: ViewHolder, position: Int) {
        (context as MainActivity).supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, RepoDetailFragment(repos[position]))
            .addToBackStack(null)
            .commit()


    }

    private fun openAuthorDetails(holder: ViewHolder, position: Int) {
        (context as MainActivity).supportFragmentManager
        .beginTransaction()
            .replace(R.id.fragment, AuthorFragment(repos[position]))
            .addToBackStack(null)
            .commit()
    }
}