package com.cacagdas.githubrepolisting.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cacagdas.githubrepolisting.MainActivity

import com.cacagdas.githubrepolisting.R
import com.cacagdas.githubrepolisting.vo.Repo
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val repoListAdapter =
        RepoListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = getString(R.string.home_fragment_title)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        reposList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoListAdapter
        }

        submitButton.setOnClickListener {
            reposList.visibility = View.GONE
            listError.visibility = View.GONE
            progressBar.visibility = View.GONE
            viewModel.refresh(usernameEditText.text.toString().trim())
            usernameEditText.text.clear()
        }
        viewModel.getAllFavedRepos()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.reposLiveData.observe(this, Observer { repos: List<Repo> ->
            repos.let {
                reposList.visibility = View.VISIBLE
                repoListAdapter.updateRepoList(repos)
            }
        })

        viewModel.reposLoadError.observe(this, Observer { isError: Boolean ->
            isError.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.reposLoading.observe(this, Observer {isLoading ->
            isLoading.let {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    reposList.visibility = View.GONE
                }
            }
        })

        viewModel.favedRepos.observe(this, Observer { favedRepos ->
            favedRepos.let {
                repoListAdapter.setFavedRepos(favedRepos)
                reposList.apply {
                    adapter = null
                    adapter = repoListAdapter
                }
            }
        })
    }
}
