/*
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
import com.t4zb.e_commerce.ui.listener.DeleteBasketItemListener

class BasketAdapter(
    private val products: List<ProductRoom>,
    deleteBasketItemListener: DeleteBasketItemListener
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private val deleteBasketItemListener = deleteBasketItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.basket_list_basket_item, parent, false)
        return BasketViewHolder(view, deleteBasketItemListener)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class BasketViewHolder(itemView: View, deleteBasketItemListener: DeleteBasketItemListener) :
        RecyclerView.ViewHolder(itemView) {
        private val deleteBasketItemListener = deleteBasketItemListener

        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productSize: TextView = itemView.findViewById(R.id.product_size)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val decreaseQuantity: MaterialCardView =
            itemView.findViewById(R.id.decrease_quantity_button)
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
                deleteBasketItemListener.onItemClicked(product)
            }
        }
    }
}
*/


package com.t4zb.e_commerce.ui.adapter

import android.annotation.SuppressLint
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
import com.t4zb.e_commerce.ui.listener.DeleteBasketItemListener

class BasketAdapter(
    private val products: List<ProductRoom>,
    private val deleteBasketItemListener: DeleteBasketItemListener
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private var showDecreaseQuantity = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.basket_list_basket_item, parent, false)
        return BasketViewHolder(view, deleteBasketItemListener)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(products[position], showDecreaseQuantity)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDecreaseQuantityVisibility(visible: Boolean) {
        showDecreaseQuantity = visible
        notifyDataSetChanged()
    }

    class BasketViewHolder(itemView: View, private val deleteBasketItemListener: DeleteBasketItemListener) :
        RecyclerView.ViewHolder(itemView) {

        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productSize: TextView = itemView.findViewById(R.id.product_size)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val decreaseQuantity: MaterialCardView = itemView.findViewById(R.id.decrease_quantity_button)
        private val quantityText: TextView = itemView.findViewById(R.id.quantity_text)

        fun bind(product: ProductRoom, showDecreaseQuantity: Boolean) {
            productName.text = product.productName
            productSize.text = "Size: ${product.productSize}"
            productPrice.text = product.productPrice.toString()
            quantityText.text = "1"

            Glide.with(productImage.context)
                .load(product.productPicture)
                .into(productImage)

            decreaseQuantity.visibility = if (showDecreaseQuantity) View.VISIBLE else View.INVISIBLE

            decreaseQuantity.setOnClickListener {
                deleteBasketItemListener.onItemClicked(product)
            }
        }
    }
}
