package com.lemonade.aidl.appclient

import android.os.IBinder
import android.util.Log
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV1
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV2
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV3
import com.lemonade.aidl.aidlcommon.calculator.Numbers

private const val TAG = "CalculatorClient"

interface CalculatorFeatures {
    fun add(num1: Int, num2: Int): Result<Int>
    fun times(num1: Int, num2: Int): Result<Int>
    fun sub(num1: Int, num2: Int): Result<Int>
    fun div(num1: Int, num2: Int): Result<Int>

    fun mod(num1: Int, num2: Int): Result<Int>
}

class CalculatorContractV1Proxy(
    val service: IBinder?
): CalculatorFeatures{

    private val contract by lazy<ICalculatorContractV1?> {
        ICalculatorContractV1.Stub.asInterface(service)
    }

    override fun add(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 add: $num1, $num2")
        contract?.let {
            return Result.success(it.add(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun times(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 times: $num1, $num2")
        contract?.let {
            return Result.success(it.times(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun sub(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 sub: $num1, $num2")
        contract?.let {
            return Result.success(it.sub(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun div(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 div: $num1, $num2")
        contract?.let {
            return Result.success(it.div(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun mod(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 mod: $num1, $num2")
        return Result.failure(Throwable("Método não compatível, atualize o seu serviço"))
    }
}

class CalculatorContractV2Proxy(
    val service: IBinder?
): CalculatorFeatures {

    private val contract by lazy<ICalculatorContractV2?> {
        ICalculatorContractV2.Stub.asInterface(service)
    }

    override fun add(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 add: $num1, $num2")
        contract?.let {
            return Result.success(it.add(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun times(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 times: $num1, $num2")
        contract?.let {
            return Result.success(it.times(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun sub(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 sub: $num1, $num2")
        contract?.let {
            return Result.success(it.sub(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun div(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 div: $num1, $num2")
        contract?.let {
            return Result.success(it.div(num1, num2, 0))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun mod(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 mod: $num1, $num2")
        contract?.let {
            return Result.success(it.mod(num1, num2))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }
}

class CalculatorContractV3Proxy(
    val service: IBinder?
): CalculatorFeatures {

    private val contract by lazy<ICalculatorContractV3?> {
        ICalculatorContractV3.Stub.asInterface(service)
    }

    override fun add(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v3 add: $num1, $num2")
        contract?.let {
            val result = it.add(Numbers(num1, num2))
            result.operationResult?.let { operationResult ->
                return Result.success(operationResult)
            }
            return Result.failure(Throwable("Service returned null result"))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun times(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v3 times: $num1, $num2")
        contract?.let {
            val result = it.times(Numbers(num1, num2))
            result.operationResult?.let { operationResult ->
                return Result.success(operationResult)
            }
            return Result.failure(Throwable("Service returned null result"))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun sub(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v3 sub: $num1, $num2")
        contract?.let {
            val result = it.sub(Numbers(num1, num2))
            result.operationResult?.let { operationResult ->
                return Result.success(operationResult)
            }
            return Result.failure(Throwable("Service returned null result"))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun div(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v3 div: $num1, $num2")
        contract?.let {
            val result = it.div(Numbers(num1, num2))
            result.operationResult?.let { operationResult ->
                return Result.success(operationResult)
            }
            return Result.failure(Throwable("Service returned null result"))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }

    override fun mod(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v3 mod: $num1, $num2")
        contract?.let {
            val result = it.mod(Numbers(num1, num2))
            result.operationResult?.let { operationResult ->
                return Result.success(operationResult)
            }
            return Result.failure(Throwable("Service returned null result"))
        }
        return Result.failure(Throwable("Não foi possivel se conectar com o serviço"))
    }
}