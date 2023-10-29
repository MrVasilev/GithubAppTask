package com.emerchantpay.githubapptask.ui.user.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.emerchantpay.githubapptask.R
import com.emerchantpay.githubapptask.databinding.FragmentUserProfileBinding
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.model.UserType
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.user.profile.adapter.RepositoryItemAdapter
import com.emerchantpay.githubapptask.ui.user.search.UserSearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var dataBinding: FragmentUserProfileBinding

    private val viewModel: UserProfileViewModel by viewModels()

    private val repositoryItemsAdapter = RepositoryItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.rvRepositories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoryItemsAdapter
        }

        dataBinding.btnFollowings.setOnClickListener { showUsersSearchScreen(UserType.FOLLOWING) }
        dataBinding.btnFollowers.setOnClickListener { showUsersSearchScreen(UserType.FOLLOWER) }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                handleUserUiState()
                handleReposUiState()
            }
        }
    }

    private fun showUsersSearchScreen(userType: UserType) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, UserSearchFragment.newInstance(userType))
            .addToBackStack(null)
            .commit()
    }

    private fun handleUserUiState() {
        viewModel.userUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                UIState.Loading -> showLoading()
                is UIState.Success -> {
                    hideLoading()
                    loadUserData(uiState.data)
                }

                is UIState.Error -> hideLoading()
            }
        }
    }

    private fun handleReposUiState() {
        viewModel.reposUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                UIState.Loading -> showLoading()
                is UIState.Success -> {
                    hideLoading()
                    loadReposData(uiState.data)
                }

                is UIState.Error -> hideLoading()
            }
        }
    }

    private fun loadUserData(user: User) {
        dataBinding.apply {
            ivProfilePhoto.load(user.avatarUrl)
            tvUserName.text = user.name
            btnFollowers.text = String.format(
                getString(R.string.followers_temp_text),
                user.followers.toString()
            )
            btnFollowings.text = String.format(
                getString(R.string.followings_temp_text),
                user.following.toString()
            )
        }
    }

    private fun loadReposData(repos: List<Repository>): Unit =
        repositoryItemsAdapter.setRepositoryItems(repos)

    private fun showLoading() {
        dataBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        dataBinding.pbLoading.visibility = View.GONE
    }

    companion object {
        fun newInstance() = UserProfileFragment()
    }

}