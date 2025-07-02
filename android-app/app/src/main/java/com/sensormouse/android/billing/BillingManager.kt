package com.sensormouse.android.billing

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillingManager(private val context: Context) {
    
    companion object {
        private const val TAG = "BillingManager"
        const val PRO_VERSION_SKU = "sensormouse_pro_version"
        const val PRO_VERSION_PRICE = 3.99
    }
    
    private lateinit var billingClient: BillingClient
    
    private val _isProUser = MutableStateFlow(false)
    val isProUser: StateFlow<Boolean> = _isProUser.asStateFlow()
    
    private val _purchaseStatus = MutableStateFlow<PurchaseStatus>(PurchaseStatus.NotPurchased)
    val purchaseStatus: StateFlow<PurchaseStatus> = _purchaseStatus.asStateFlow()
    
    init {
        setupBillingClient()
    }
    
    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(context)
            .setListener { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        handlePurchase(purchase)
                    }
                } else {
                    Log.w(TAG, "Billing error: ${billingResult.debugMessage}")
                }
            }
            .enablePendingPurchases()
            .build()
        
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing client connected")
                    queryPurchases()
                } else {
                    Log.e(TAG, "Billing setup failed: ${billingResult.debugMessage}")
                }
            }
            
            override fun onBillingServiceDisconnected() {
                Log.w(TAG, "Billing service disconnected")
            }
        })
    }
    
    fun launchBillingFlow(activity: Activity) {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRO_VERSION_SKU)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )
        
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()
        
        billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val productDetails = productDetailsList.firstOrNull()
                if (productDetails != null) {
                    val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken
                    val productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .build()
                    )
                    
                    val billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build()
                    
                    billingClient.launchBillingFlow(activity, billingFlowParams)
                }
            }
        }
    }
    
    private fun queryPurchases() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val proPurchase = purchases.find { it.products.contains(PRO_VERSION_SKU) }
                if (proPurchase != null && proPurchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    _isProUser.value = true
                    _purchaseStatus.value = PurchaseStatus.Purchased
                } else {
                    _isProUser.value = false
                    _purchaseStatus.value = PurchaseStatus.NotPurchased
                }
            }
        }
    }
    
    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                
                billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        _isProUser.value = true
                        _purchaseStatus.value = PurchaseStatus.Purchased
                        Log.d(TAG, "Purchase acknowledged successfully")
                    }
                }
            } else {
                _isProUser.value = true
                _purchaseStatus.value = PurchaseStatus.Purchased
            }
        }
    }
    
    fun disconnect() {
        if (::billingClient.isInitialized) {
            billingClient.endConnection()
        }
    }
}

sealed class PurchaseStatus {
    object NotPurchased : PurchaseStatus()
    object Purchased : PurchaseStatus()
    object Pending : PurchaseStatus()
    data class Error(val message: String) : PurchaseStatus()
} 