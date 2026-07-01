package com.example.ohmygavra_kotlin.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ohmygavra_kotlin.R
import com.example.ohmygavra_kotlin.databinding.ActivityProductDetailBinding
import com.example.ohmygavra_kotlin.domain.model.Product

// Presentation: pantalla de detalle que solo renderiza el estado recibido del ViewModel.
class ProductDetailActivity : AppCompatActivity() {

    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModelFactory() }
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindEvents()
        observeState()

        val productId = intent.getIntExtra(EXTRA_PRODUCT_ID, INVALID_PRODUCT_ID)
        viewModel.loadProduct(productId)
    }

    private fun bindEvents() {
        binding.detailBackButton.setOnClickListener { finish() }
    }

    private fun observeState() {
        viewModel.uiState.observe(this) { state ->
            render(state)
        }
    }

    private fun render(state: ProductDetailUiState) {
        binding.detailLoadingProgressBar.visibility = View.GONE
        binding.detailContentScrollView.visibility = View.GONE
        binding.detailErrorLayout.visibility = View.GONE

        when (state) {
            ProductDetailUiState.Loading -> {
                binding.detailLoadingProgressBar.visibility = View.VISIBLE
            }

            is ProductDetailUiState.Success -> {
                bindProduct(state.product)
                binding.detailContentScrollView.visibility = View.VISIBLE
            }

            is ProductDetailUiState.Error -> {
                binding.detailErrorTextView.text = state.message
                binding.detailErrorLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun bindProduct(product: Product) {
        binding.detailProductImageView.setImageResource(product.imagenResId)
        binding.detailNameTextView.text = product.nombre
        binding.detailPriceTextView.text = getString(R.string.product_price_format, product.precio)
        binding.detailDescriptionTextView.text = product.descripcion
        binding.detailStockTextView.text = if (product.stock > 0) {
            getString(R.string.product_stock_format, product.stock)
        } else {
            getString(R.string.product_without_stock)
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "extra_product_id"
        private const val INVALID_PRODUCT_ID = -1

        fun createIntent(context: Context, productId: Int): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
        }
    }
}
