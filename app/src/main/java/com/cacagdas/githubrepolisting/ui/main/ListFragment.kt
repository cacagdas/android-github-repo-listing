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
import com.cacagdas.githubrepolisting.api.DataHolder
import com.cacagdas.githubrepolisting.vo.Repo
import kotlinx.android.synthetic.main.fragment_list.*

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
            usernameEditText.text?.clear()
        }
        viewModel.getAllFavedRepos()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.processTraceLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Success -> {
                    when (it.data) {
                        is List<*> -> {
                            reposList.visibility = View.VISIBLE
                            repoListAdapter.updateRepoList(it.data as List<Repo>)
                            progressBar.visibility = View.GONE
                            listError.visibility = View.GONE
                        }
                    }
                }
                is DataHolder.Loading ->  progressBar.visibility = View.VISIBLE
                is DataHolder.Fail -> {
                    progressBar.visibility = View.GONE
                    listError.visibility = View.VISIBLE
                }
            }
        })

        viewModel.favedRepos.observe(viewLifecycleOwner, Observer { favedRepos ->
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
