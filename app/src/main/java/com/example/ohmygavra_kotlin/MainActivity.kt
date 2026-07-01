package com.example.ohmygavra_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.ohmygavra_kotlin.databinding.ActivityMainBinding
import com.example.ohmygavra_kotlin.presentation.login.LoginUiState
import com.example.ohmygavra_kotlin.presentation.login.LoginViewModel
import com.example.ohmygavra_kotlin.presentation.login.LoginViewModelFactory
import com.example.ohmygavra_kotlin.presentation.catalog.CatalogActivity
import com.example.ohmygavra_kotlin.presentation.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory() }

    private lateinit var binding: ActivityMainBinding
    private var hasNavigatedToCatalog = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindEvents()
        observeState()
    }

    private fun bindEvents() {
        binding.emailEditText.doAfterTextChanged { viewModel.onEmailChanged(it.toString()) }
        binding.passwordEditText.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }
        binding.loginButton.setOnClickListener { viewModel.onLoginClicked() }
        binding.createAccountButton.setOnClickListener { navigateToRegister() }
        binding.passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onLoginClicked()
                true
            } else {
                false
            }
        }
    }

    private fun observeState() {
        viewModel.uiState.observe(this) { state ->
            render(state)
        }
    }

    private fun render(state: LoginUiState) {
        binding.loginButton.isEnabled = state.isLoginEnabled
        binding.emailInputLayout.error = state.emailError
        binding.passwordInputLayout.error = state.passwordError

        when {
            state.successMessage != null -> {
                binding.statusTextView.text = state.successMessage
                binding.statusTextView.setTextColor(ContextCompat.getColor(this, R.color.success))
                navigateToCatalog()
            }

            state.generalError != null -> {
                binding.statusTextView.text = state.generalError
                binding.statusTextView.setTextColor(ContextCompat.getColor(this, R.color.error))
            }

            else -> {
                binding.statusTextView.text = ""
            }
        }
    }

    private fun navigateToCatalog() {
        if (hasNavigatedToCatalog) return

        hasNavigatedToCatalog = true
        startActivity(Intent(this, CatalogActivity::class.java))
        finish()
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
