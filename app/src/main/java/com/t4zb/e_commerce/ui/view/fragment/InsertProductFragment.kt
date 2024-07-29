package com.t4zb.e_commerce.ui.view.fragment

import android.app.Activity
import android.app.Activity.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.databinding.FragmentComplateRegisterBinding
import com.t4zb.e_commerce.databinding.FragmentInsertProductBinding
import com.t4zb.e_commerce.ui.viewmodel.CompleteRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO: this fragment is a insert fragment for only insert data on firebase, will be delete when more data is upload on database
@AndroidEntryPoint
class InsertProductFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentInsertProductBinding
    private val pickImageRequestCode = 1997
    private val productImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insert_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentInsertProductBinding.bind(view)

        mBinding.btnAddPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, pickImageRequestCode)
        }

        mBinding.btnSubmitProduct.setOnClickListener {
            submitProduct()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequestCode && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                uploadImageToFirebase(uri)
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val storageReference = FirebaseStorage.getInstance().reference.child("product_images")
        val ref = storageReference.child("${System.currentTimeMillis()}.jpg")
        ref.putFile(imageUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { downloadUri ->
                    productImages.add(downloadUri.toString())
                    Toast.makeText(mContext, "Image Uploaded Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    mContext,
                    "Image Upload Failed: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun submitProduct() {
        val productName = mBinding.etProductName.text.toString()
        val productDescription = mBinding.etProductDescription.text.toString()
        val productPrice = mBinding.etProductPrice.text.toString().toDoubleOrNull()
        val productCategory = mBinding.etProductCategory.text.toString()
        val productStockCount = mBinding.etProductStockCount.text.toString().toIntOrNull()
        val productOwner = mBinding.etProductOwner.text.toString()
        val productColor = mBinding.etProductColor.text.toString()
        val productSize1 = mBinding.etProductSize1.text.toString()
        val productSize2 = mBinding.etProductSize2.text.toString()
        val productSize3 = mBinding.etProductSize3.text.toString()
        val productSize4 = mBinding.etProductSize4.text.toString()
        val productSize5 = mBinding.etProductSize5.text.toString()
        val productSize6 = mBinding.etProductSize6.text.toString()
        val productLabel = mBinding.etProductLabel.text.toString()
        val productStar = mBinding.etProductStar.text.toString().toDoubleOrNull()


        if (productName.isNotEmpty() && productDescription.isNotEmpty() && productPrice != null &&
            productCategory.isNotEmpty() && productStockCount != null && productOwner.isNotEmpty() &&
            productColor.isNotEmpty() && productSize1.isNotEmpty() && productSize2.isNotEmpty() &&
            productLabel.isNotEmpty() && productStar != null
        ) {

            val productSizeList = listOf(
                productSize1,
                productSize2,
                productSize3,
                productSize4,
                productSize5,
                productSize6
            )

            val product = Product(
                productName = productName,
                productDescription = productDescription,
                productPrice = productPrice,
                productCategory = productCategory,
                productStockCount = productStockCount,
                productPictureList = productImages,
                productOwner = productOwner,
                productColor = productColor,
                productSize = null, // Bu alan artık kullanılmıyor
                productSizeList = productSizeList,
                productLabel = productLabel,
                star = productStar
            )

            addProductToFirebase(product)
        } else {
            Toast.makeText(mContext, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addProductToFirebase(product: Product) {
        val databaseReference =
            FirebaseDatabase
                .getInstance("https://ecommerce-f6145-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference.child(
                    "products"
                )
        val productId = databaseReference.push().key ?: return
        product.productId = productId // Set the productId

        databaseReference.child(productId).setValue(product)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(mContext, "Product Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(mContext, "Product Addition Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}