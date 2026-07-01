package com.example.ohmygavra_kotlin.presentation.catalog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ohmygavra_kotlin.MainActivity
import com.example.ohmygavra_kotlin.databinding.ActivityCatalogBinding
import com.example.ohmygavra_kotlin.presentation.productdetail.ProductDetailActivity

// Presentation: pantalla que observa el ViewModel y renderiza el catalogo.
class CatalogActivity : AppCompatActivity() {

    private val viewModel: CatalogViewModel by viewModels { CatalogViewModelFactory() }
    private lateinit var binding: ActivityCatalogBinding
    private val productAdapter = ProductAdapter { product ->
        navigateToProductDetail(product.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        bindEvents()
        observeState()
    }

    private fun setupRecyclerView() {
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productRecyclerView.adapter = productAdapter
    }

    private fun bindEvents() {
        binding.logoutButton.setOnClickListener { navigateToLogin() }
        binding.retryButton.setOnClickListener { viewModel.loadProducts() }
    }

    private fun observeState() {
        viewModel.uiState.observe(this) { state ->
            render(state)
        }
    }

    private fun render(state: CatalogUiState) {
        binding.loadingProgressBar.visibility = View.GONE
        binding.productRecyclerView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorStateLayout.visibility = View.GONE

        when (state) {
            CatalogUiState.Loading -> {
                binding.loadingProgressBar.visibility = View.VISIBLE
            }

            is CatalogUiState.Success -> {
                productAdapter.submitList(state.products)
                binding.productRecyclerView.visibility = View.VISIBLE
            }

            CatalogUiState.Empty -> {
                binding.emptyStateLayout.visibility = View.VISIBLE
            }

            is CatalogUiState.Error -> {
                binding.errorTitleTextView.text = state.message
                binding.errorStateLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateToProductDetail(productId: Int) {
        startActivity(ProductDetailActivity.createIntent(this, productId))
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
