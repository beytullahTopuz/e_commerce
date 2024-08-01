package com.t4zb.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.model.Order
import com.t4zb.e_commerce.data.model.ProductRoom

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_BODY = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) VIEW_TYPE_HEADER else VIEW_TYPE_BODY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_body, parent, false)
            BodyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val (order, product) = getOrderAndProductForPosition(position)
        if (holder is HeaderViewHolder && order != null) {
            holder.bind(order)
        } else if (holder is BodyViewHolder && product != null) {
            holder.bind(product)
        }
    }

    override fun getItemCount(): Int {
        return orders.sumOf { it.basketProductList.size + 1 }
    }

    private fun isHeader(position: Int): Boolean {
        var currentPosition = 0
        orders.forEach { order ->
            if (currentPosition == position) return true
            currentPosition += order.basketProductList.size + 1
            if (currentPosition > position) return false
        }
        return false
    }

    private fun getOrderAndProductForPosition(position: Int): Pair<Order?, ProductRoom?> {
        var currentPosition = 0
        orders.forEach { order ->
            if (currentPosition == position) return Pair(order, null)
            currentPosition++
            if (currentPosition + order.basketProductList.size > position) {
                val productIndex = position - currentPosition
                return Pair(null, order.basketProductList[productIndex])
            }
            currentPosition += order.basketProductList.size
        }
        return Pair(null, null)
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOrderCreateDate: TextView = itemView.findViewById(R.id.tvOrderCreateDate)
        private val tvOrderItemSize: TextView = itemView.findViewById(R.id.tvOrderItemSize)
        private val tvOrderTotalPrice: TextView = itemView.findViewById(R.id.tvOrderTotalPrice)

        fun bind(order: Order) {
            tvOrderCreateDate.text = order.orderCreateDate
            tvOrderItemSize.text = "Items: ${order.basketProductList.size}"
            val totalPrice = order.basketProductList.sumOf { it.productPrice ?: 0.0 }
            tvOrderTotalPrice.text = "Total Price: $$totalPrice"
        }
    }

    class BodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)

        fun bind(product: ProductRoom) {
            tvProductName.text = product.productName
            tvProductPrice.text = "Price: $${product.productPrice}"
        }
    }
}
