package com.t4zb.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.data.model.ProductRoom

class BasketAdapter(private val products: List<ProductRoom>) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.basket_list_basket_item, parent, false)
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productSize: TextView = itemView.findViewById(R.id.product_size)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val decreaseQuantity: MaterialCardView = itemView.findViewById(R.id.decrease_quantity_button)
        private val quantityText: TextView = itemView.findViewById(R.id.quantity_text)

        fun bind(product: ProductRoom) {
            productName.text = product.productName
            productSize.text = "Size: ${product.productSize}"
            productPrice.text = product.productPrice.toString()
            quantityText.text = "1"

            Glide.with(productImage.context)
                .load(product.productPicture)
                .into(productImage)

            decreaseQuantity.setOnClickListener {
                val currentQuantity = quantityText.text.toString().toInt()
                if (currentQuantity > 1) {
                    quantityText.text = (currentQuantity - 1).toString()
                }
            }

        }
    }
}
