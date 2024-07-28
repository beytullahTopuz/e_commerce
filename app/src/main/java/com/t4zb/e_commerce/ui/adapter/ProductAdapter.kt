package com.t4zb.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.model.Product

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productStar: TextView = itemView.findViewById(R.id.productStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.productName
        holder.productPrice.text = product.productPrice.toString()
        holder.productStar.text = product.star.toString()
        Glide.with(holder.productImage.context)
            .load(product.productPictureList?.get(0))
            .into(holder.productImage)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}