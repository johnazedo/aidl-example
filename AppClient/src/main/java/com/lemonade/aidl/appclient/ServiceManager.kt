package com.lemonade.aidl.appclient

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV1
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV2
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV3

private const val TAG = "Teste"

class ServiceManager(
    private val onConnected: (Result<CalculatorFeatures>) -> Unit
): ServiceConnection{

    private var calculatorFeature: CalculatorFeatures? = null
    private var binderFeature: BinderFeatures? = null

    fun getFeatures(): Result<CalculatorFeatures> {
        if(calculatorFeature == null) return Result.failure(Throwable("Não foi possível se conectar ao serviço"))
        return Result.success(calculatorFeature!!)
    }

    override fun onServiceConnected(
        name: ComponentName?,
        service: IBinder?
    ) {
        Log.d(TAG, "Calculator service connected")
        when (service?.interfaceDescriptor) {
            ICalculatorContractV2.DESCRIPTOR -> {
                Log.d(TAG, "Connected to CalculatorService V2")
                val serviceV2 = ICalculatorContractV2.Stub.asInterface(service)
                val calculator = CalculatorV2(serviceV2)
                calculatorFeature = calculator
                binderFeature = calculator
            }
            ICalculatorContractV1.DESCRIPTOR -> {
                Log.d(TAG, "Connected to CalculatorService V1")
                val serviceV1 = ICalculatorContractV1.Stub.asInterface(service)
                val calculator = CalculatorV1(serviceV1)
                calculatorFeature = calculator
                binderFeature = calculator
            }
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        binderFeature?.setNull()
    }

}