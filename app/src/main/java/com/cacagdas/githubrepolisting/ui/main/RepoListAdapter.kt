package com.cacagdas.githubrepolisting.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cacagdas.githubrepolisting.R
import com.cacagdas.githubrepolisting.databinding.ItemRepoBinding
import com.cacagdas.githubrepolisting.vo.Favorite
import com.cacagdas.githubrepolisting.vo.Repo
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter(private val repoList: ArrayList<Repo>): RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>(), RepoClickListener {

    private var favedRepos: List<Favorite>? = null

    fun updateRepoList(newRepoList: List<Repo>) {
        repoList.clear()
        repoList.addAll(newRepoList)
        notifyDataSetChanged()
    }

    fun setFavedRepos(repos: List<Favorite>) {
        favedRepos = repos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRepoBinding>(inflater, R.layout.item_repo, parent, false)
        return RepoViewHolder(
            view
        )
    }

    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.view.repo = repoList[position]
        holder.view.listener = this
        if (!favedRepos.isNullOrEmpty()) {
            for (repo in favedRepos!!) {
                if (repo.id == holder.view.repo!!.id)
                    holder.view.imageView.visibility = View.VISIBLE
            }
        }
    }

    override fun onRepoClicked(v: View) {
        val id = v.repoId.text.toString().toInt()
        val name = v.repoName.text.toString()
        val action =
            ListFragmentDirections.actionListFragmentToDetailFragment()
        action.repoUuid = id
        action.repoName = name
        Navigation.findNavController(v).navigate(action)
    }

    class RepoViewHolder(var view: ItemRepoBinding): RecyclerView.ViewHolder(view.root)
}