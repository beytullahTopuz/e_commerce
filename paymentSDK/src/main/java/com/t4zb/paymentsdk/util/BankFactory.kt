package com.t4zb.paymentsdk.util

import com.t4zb.paymentsdk.Bank
import com.t4zb.paymentsdk.bank.DenizBank
import com.t4zb.paymentsdk.bank.FinansBank
import com.t4zb.paymentsdk.bank.GarantiBank
import com.t4zb.paymentsdk.bank.SekerBank
import com.t4zb.paymentsdk.bank.YapikrediBank
import com.t4zb.paymentsdk.bank.ZiraatBank

object BankFactory {
    fun getBank(cardNo: String): Bank {
        return when {
            cardNo.startsWith("1") -> ZiraatBank()
            cardNo.startsWith("2") -> SekerBank()
            cardNo.startsWith("3") -> FinansBank()
            cardNo.startsWith("4") -> GarantiBank()
            cardNo.startsWith("5") -> DenizBank()
            cardNo.startsWith("6") -> YapikrediBank()
            else -> ZiraatBank()
        }
    }
}