package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.Basket
import com.t4zb.e_commerce.databinding.FragmentPaymentBinding
import com.t4zb.e_commerce.ui.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private var basketId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPaymentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val amount = arguments?.let {
            PaymentFragmentArgs.fromBundle(it).amount
        }

        basketId = arguments?.let {
            PaymentFragmentArgs.fromBundle(it).basketId
        }

        mBinding.etCardNumber.addTextChangedListener { text ->
            viewModel.onCardNumberChanged(text.toString())
        }

        viewModel.cardNumber.observe(viewLifecycleOwner, Observer { cardNumber ->
            mBinding.cardNumber.text = cardNumber
        })

        viewModel.cardNumberError.observe(viewLifecycleOwner, Observer { error ->
            mBinding.tilCardNumber.error = error
        })

        mBinding.startPaymentBtn.setOnClickListener {
            val cardNumber = mBinding.etCardNumber.text.toString()
            val cardHolderName = mBinding.etCardHolderName.text.toString()
            val expiryMonth = mBinding.etExpiryMonth.text.toString()
            val expiryYear = mBinding.etExpiryYear.text.toString()
            val cvv = mBinding.etCvv.text.toString()

            amount?.let { totalPrice ->
                viewModel.startPayment(
                    totalPrice.toDouble(),
                    cardNumber,
                    cardHolderName,
                    expiryMonth,
                    expiryYear,
                    cvv
                )
            }
        }

        viewModel.otpRequired.observe(viewLifecycleOwner, Observer { isOtpRequired ->
            if (isOtpRequired) {
                showOtpDialog()
            }
        })

        viewModel.paymentResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(mContext, "Payment successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(mContext, "Payment failed", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.confirmationResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(mContext, "OTP confirmation successful", Toast.LENGTH_SHORT).show()

                basketId?.let { viewModel.deleteBasket(it) }
                AppConfig.currentSelectedBasket = null
                AppConfig.currentSelectedProduct = null
                findNavController().navigateUp()
                findNavController().navigateUp()
            } else {
                Toast.makeText(mContext, "OTP confirmation failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showOtpDialog() {
        val dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_otp_input, null)
        val otpEditText =
            dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etOtp)

        val alertDialog = MaterialAlertDialogBuilder(mContext)
            .setTitle("OTP Confirmation")
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.confirmOtpBtn)
            .setOnClickListener {
                val otp = otpEditText.text.toString()
                viewModel.confirmPayment(otp)
                alertDialog.dismiss()
            }

        alertDialog.show()
    }
}