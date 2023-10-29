package com.emerchantpay.githubapptask.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.emerchantpay.githubapptask.R
import com.emerchantpay.githubapptask.databinding.FragmentRepoBinding
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.ui.common.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepoFragment : Fragment() {

    private lateinit var dataBinding: FragmentRepoBinding

    private val viewModel: RepoViewModel by viewModels()

    private val ownerName: String by lazy {
        arguments?.getString(OWNER_REPO_NAME_PARAM)
            ?: throw IllegalArgumentException("Repo ID not found! Use newInstance(ownerName, repoId) function!")
    }

    private val repoId: Long by lazy {
        arguments?.getLong(REPO_ID_PARAM)
            ?: throw IllegalArgumentException("Repo ID not found! Use newInstance(ownerName, repoId) function!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_repo, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                handleRepoUiState()
                handleRepoOwnerUiState()
            }
        }

        viewModel.init(ownerName, repoId)
    }

    private fun handleRepoUiState() {
        viewModel.repoUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                UIState.Loading -> showLoading()
                is UIState.Success -> {
                    hideLoading()
                    loadRepoData(uiState.data)
                }

                is UIState.Error -> hideLoading()
            }
        }
    }

    private fun handleRepoOwnerUiState() {
        viewModel.userUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                UIState.Loading -> showLoading()
                is UIState.Success -> {
                    hideLoading()
                    loadRepoOwnerData(uiState.data)
                }

                is UIState.Error -> hideLoading()
            }
        }
    }

    private fun loadRepoData(repo: Repository) {
        dataBinding.tvRepoName.text = repo.name
    }

    private fun loadRepoOwnerData(user: User) {
        dataBinding.apply {
            tvRepoOwnerName.text = user.name
            ivRepoOwnerPhoto.load(user.avatarUrl)
        }
    }

    private fun showLoading() {
        dataBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        dataBinding.pbLoading.visibility = View.GONE
    }

    companion object {
        private const val REPO_ID_PARAM = "REPO_ID"
        private const val OWNER_REPO_NAME_PARAM = "OWNER_REPO_NAME"

        fun newInstance(ownerName: String, repoId: Long) = RepoFragment().apply {
            arguments = bundleOf(
                OWNER_REPO_NAME_PARAM to ownerName,
                REPO_ID_PARAM to repoId
            )
        }
    }

}