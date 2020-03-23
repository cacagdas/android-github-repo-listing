package com.cacagdas.githubrepolisting.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cacagdas.githubrepolisting.MainActivity

import com.cacagdas.githubrepolisting.R
import com.cacagdas.githubrepolisting.databinding.FragmentDetailBinding
import com.cacagdas.githubrepolisting.ui.main.RepoListAdapter
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    private var repoUuid = 0
    private var repoName = ""
    private var isFaved = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            repoUuid = DetailFragmentArgs.fromBundle(
                it
            ).repoUuid
            repoName = DetailFragmentArgs.fromBundle(it).repoName
        }

        (activity as MainActivity).supportActionBar?.title = repoName

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(repoUuid)
        viewModel.checkIsRepoFaved(repoUuid)

        observeViewModel()

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun setupButton() {
        if (!isFaved)
            favoriteButton.setImageResource(R.drawable.ic_star_white)
        else
            favoriteButton.setImageResource(R.drawable.ic_star)

        favoriteButton.setOnClickListener {
            viewModel.addRepoToFavorites(repoUuid)
        }
    }

    private fun observeViewModel() {
        viewModel.repoLiveData.observe(this, Observer {repo ->
            repo?.let {
                dataBinding.repo = repo
            }
        })
        viewModel.isFavorite.observe(this, Observer { it ->
            it?.let {
                isFaved = it
                setupButton()
            }
        })
    }
}
