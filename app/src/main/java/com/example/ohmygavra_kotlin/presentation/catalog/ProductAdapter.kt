package com.example.ohmygavra_kotlin.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ohmygavra_kotlin.R
import com.example.ohmygavra_kotlin.databinding.ItemProductBinding
import com.example.ohmygavra_kotlin.domain.model.Product

// Presentation: adapta productos de dominio a filas visuales del RecyclerView.
class ProductAdapter(
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val products = mutableListOf<Product>()

    fun submitList(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding, onProductClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productImageView.setImageResource(product.imagenResId)
            binding.productNameTextView.text = product.nombre
            binding.productDescriptionTextView.text = product.descripcion
            binding.productPriceTextView.text = binding.root.context.getString(
                R.string.product_price_format,
                product.precio
            )
            binding.root.setOnClickListener { onProductClick(product) }
        }
    }
}
