package com.lemonade.aidl.appclient

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.util.LogPrinter
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV1
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV2
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV3

private const val TAG = "Teste"

class ServiceManager(
    private val onConnected: (Result<CalculatorFeatures>) -> Unit,
    private val onDisconnected: () -> Unit = {}
): ServiceConnection{

    private var calculatorFeature: CalculatorFeatures? = null

    fun getFeatures(): Result<CalculatorFeatures> {
        if(calculatorFeature == null) return Result.failure(Throwable("Não foi possível se conectar ao serviço"))
        return Result.success(calculatorFeature!!)
    }

    override fun onServiceConnected(
        name: ComponentName?,
        service: IBinder?
    ) {
        Log.d(TAG, "Calculator service connected")
        calculatorFeature = when (service?.interfaceDescriptor) {
            ICalculatorContractV3.DESCRIPTOR -> CalculatorContractV3Proxy(service)
            ICalculatorContractV2.DESCRIPTOR -> CalculatorContractV2Proxy(service)
            ICalculatorContractV1.DESCRIPTOR -> CalculatorContractV1Proxy(service)
            else -> null
        }
        onConnected(getFeatures())
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        onDisconnected()
        calculatorFeature = null
    }
}