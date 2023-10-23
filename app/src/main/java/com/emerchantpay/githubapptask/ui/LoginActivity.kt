package com.emerchantpay.githubapptask.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.emerchantpay.githubapptask.R
import com.emerchantpay.githubapptask.databinding.ActivityMainBinding
import com.emerchantpay.githubapptask.ui.model.UserUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                handleUserUiState()
            }
        }

        dataBinding.btnLogin.setOnClickListener {
            viewModel.onLoginButtonClicked(dataBinding.etAccessToken.text.toString())
        }
    }

    private fun handleUserUiState() {
        viewModel.userUiState.observe(this) { uiState ->
            when (uiState) {
                UserUiState.Loading -> showLoading()
                is UserUiState.Success -> {
                    hideLoading()
                    navigateToMainScreen()
                }

                is UserUiState.Error -> {
                    hideLoading()
                    dataBinding.etAccessToken.error = uiState.message
                }
            }
        }
    }

    private fun showLoading() {
        dataBinding.pgLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        dataBinding.pgLoading.visibility = View.GONE
    }

    private fun navigateToMainScreen() {

    }
}
