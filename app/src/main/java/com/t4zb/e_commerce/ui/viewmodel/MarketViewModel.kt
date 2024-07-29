package com.t4zb.e_commerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.data.repository.ProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _popularProducts = MutableLiveData<List<Product>>()
    val popularProducts: LiveData<List<Product>> get() = _popularProducts

    init {
        fetchProducts()
        fetchPopularProducts()
    }

    private fun fetchProducts() {
        productRepo.getProducts { productList ->
            _products.value = productList
        }
    }

    private fun fetchPopularProducts() {
        productRepo.getPopularProducts { products ->
            _popularProducts.value = products
        }
    }
}