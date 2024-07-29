package com.t4zb.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.model.Product

class ProductViewPagerAdapter(
    private val productList: List<Product>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_view_pager_product, container, false)

        val product = productList[position]

        val imageView: ImageView = view.findViewById(R.id.ivProductImage)
        val titleView: TextView = view.findViewById(R.id.tvProductTitle)

        Glide.with(imageView.context)
            .load(product.productPictureList?.get(0))
            .into(imageView)

        titleView.text = product.productName

        container.addView(view)
        return view
    }

    override fun getCount(): Int = productList.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
