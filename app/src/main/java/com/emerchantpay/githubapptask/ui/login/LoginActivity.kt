package com.emerchantpay.githubapptask.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.emerchantpay.githubapptask.R
import com.emerchantpay.githubapptask.databinding.ActivityLoginBinding
import com.emerchantpay.githubapptask.ui.MainActivity
import com.emerchantpay.githubapptask.ui.common.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

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
                UIState.Loading -> showLoading()
                is UIState.Success -> {
                    hideLoading()
                    navigateToMainScreen()
                }

                is UIState.Error -> {
                    hideLoading()
                    showEditFieldErrorMessage(uiState.message)
                }
            }
        }
    }

    private fun showEditFieldErrorMessage(message: String) {
        dataBinding.etAccessToken.error = message
    }

    private fun showLoading() {
        dataBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        dataBinding.pbLoading.visibility = View.GONE
    }

    private fun navigateToMainScreen() {
        MainActivity.start(this)
        finish()
    }
}
