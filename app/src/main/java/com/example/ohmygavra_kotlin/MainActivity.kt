package com.example.ohmygavra_kotlin

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.ohmygavra_kotlin.presentation.login.LoginUiState
import com.example.ohmygavra_kotlin.presentation.login.LoginViewModel
import com.example.ohmygavra_kotlin.presentation.login.LoginViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory() }

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        bindEvents()
        observeState()
    }

    private fun bindViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        statusTextView = findViewById(R.id.statusTextView)
    }

    private fun bindEvents() {
        emailEditText.doAfterTextChanged { viewModel.onEmailChanged(it.toString()) }
        passwordEditText.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }
        loginButton.setOnClickListener { viewModel.onLoginClicked() }
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
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
        loginButton.isEnabled = state.isLoginEnabled
        emailInputLayout.error = state.emailError
        passwordInputLayout.error = state.passwordError

        when {
            state.successMessage != null -> {
                statusTextView.text = state.successMessage
                statusTextView.setTextColor(ContextCompat.getColor(this, R.color.success))
            }

            state.generalError != null -> {
                statusTextView.text = state.generalError
                statusTextView.setTextColor(ContextCompat.getColor(this, R.color.error))
            }

            else -> {
                statusTextView.text = ""
            }
        }
    }
}
