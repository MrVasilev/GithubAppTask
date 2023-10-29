package com.emerchantpay.githubapptask.ui.user.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.emerchantpay.githubapptask.R
import com.emerchantpay.githubapptask.databinding.FragmentUserSearchBinding
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.user.search.adapter.UserSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserSearchFragment : Fragment() {

    private lateinit var dataBinding: FragmentUserSearchBinding

    private val viewModel: UserSearchViewModel by viewModels()

    private val userSearchAdapter = UserSearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_search, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userSearchAdapter
        }

        dataBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                userSearchAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userSearchAdapter.filter.filter(newText)
                return true
            }

        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                handleUsersData()
            }
        }

        viewModel.init()
    }

    private fun handleUsersData() {
        viewModel.usersUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                UIState.Loading -> showLoading()
                is UIState.Success -> {
                    hideLoading()
                    loadUsersData(uiState.data)
                }

                is UIState.Error -> hideLoading()
            }
        }
    }

    private fun loadUsersData(users: List<User>) {
        userSearchAdapter.setUserItems(users)
    }

    private fun showLoading() {
        dataBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        dataBinding.pbLoading.visibility = View.GONE
    }

    companion object {
        fun newInstance() = UserSearchFragment()
    }

}