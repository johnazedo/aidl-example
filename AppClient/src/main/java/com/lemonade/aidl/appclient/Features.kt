package com.lemonade.aidl.appclient

import android.util.Log
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV1
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV2

private const val TAG = "CalculatorClient"

interface CalculatorFeatures {
    fun add(num1: Int, num2: Int): Result<Int>
    fun times(num1: Int, num2: Int): Result<Int>
    fun sub(num1: Int, num2: Int): Result<Int>
    fun div(num1: Int, num2: Int): Result<Int>

    fun mod(num1: Int, num2: Int): Result<Int>
}

interface BinderFeatures {
    fun setNull();
}

class CalculatorV1(
    private var contract: ICalculatorContractV1? = null
): CalculatorFeatures, BinderFeatures{

    override fun add(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 add: $num1, $num2")
        return Result.success(contract!!.add(num1, num2))
    }

    override fun times(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 times: $num1, $num2")
        return Result.success(contract!!.times(num1, num2))
    }

    override fun sub(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 sub: $num1, $num2")

        return Result.success(contract!!.sub(num1, num2))
    }

    override fun div(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 div: $num1, $num2")
        return Result.success(contract!!.div(num1, num2))
    }

    override fun mod(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v1 mod: $num1, $num2")
        return Result.failure(Throwable("Método não compatível, atualize o seu serviço"))
    }

    override fun setNull() {
        contract = null
    }
}

class CalculatorV2(
    private var contract: ICalculatorContractV2? = null
): CalculatorFeatures, BinderFeatures {
    override fun add(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 add: $num1, $num2")
        return Result.success(contract!!.add(num1, num2))
    }

    override fun times(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 times: $num1, $num2")
        return Result.success(contract!!.times(num1, num2))
    }

    override fun sub(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 sub: $num1, $num2")
        return Result.success(contract!!.sub(num1, num2))
    }

    override fun div(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 div: $num1, $num2")
        return Result.success(contract!!.div(num1, num2, 0))
    }

    override fun mod(num1: Int, num2: Int): Result<Int> {
        Log.i(TAG, "v2 mod: $num1, $num2")
        return Result.success(contract!!.mod(num1, num2))
    }

    override fun setNull() {
        contract = null
    }
}