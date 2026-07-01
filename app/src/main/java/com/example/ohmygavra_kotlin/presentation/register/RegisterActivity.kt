package com.example.ohmygavra_kotlin.presentation.register

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.ohmygavra_kotlin.R
import com.example.ohmygavra_kotlin.databinding.ActivityRegisterBinding
import com.example.ohmygavra_kotlin.presentation.catalog.CatalogActivity

// Presentation: pantalla XML que renderiza el formulario y delega acciones al ViewModel.
class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory() }
    private lateinit var binding: ActivityRegisterBinding
    private var hasNavigatedToCatalog = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindEvents()
        observeState()
    }

    private fun bindEvents() {
        binding.nameEditText.doAfterTextChanged { viewModel.onNameChanged(it.toString()) }
        binding.registerEmailEditText.doAfterTextChanged { viewModel.onEmailChanged(it.toString()) }
        binding.registerPasswordEditText.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }
        binding.ageEditText.doAfterTextChanged { viewModel.onAgeChanged(it.toString()) }
        binding.registerButton.setOnClickListener { viewModel.onRegisterClicked() }
        binding.alreadyHaveAccountButton.setOnClickListener { finish() }
        binding.ageEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onRegisterClicked()
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

    private fun render(state: RegisterUiState) {
        binding.registerButton.isEnabled = state.isRegisterEnabled
        binding.nameInputLayout.error = state.nameError
        binding.registerEmailInputLayout.error = state.emailError
        binding.registerPasswordInputLayout.error = state.passwordError
        binding.ageInputLayout.error = state.ageError

        when {
            state.successMessage != null -> {
                binding.registerStatusTextView.text = state.successMessage
                binding.registerStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.success))
                navigateToCatalog()
            }

            state.generalError != null -> {
                binding.registerStatusTextView.text = state.generalError
                binding.registerStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.error))
            }

            else -> {
                binding.registerStatusTextView.text = ""
            }
        }
    }

    private fun navigateToCatalog() {
        if (hasNavigatedToCatalog) return

        hasNavigatedToCatalog = true
        val intent = Intent(this, CatalogActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
