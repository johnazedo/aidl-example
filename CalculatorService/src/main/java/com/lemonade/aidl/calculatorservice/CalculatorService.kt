package com.lemonade.aidl.calculatorservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV1
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV2

private const val TAG = "CalculatorService"

class CalculatorService: Service() {
    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind")
        return BinderV2()
    }

    class Binder: ICalculatorContractV1.Stub() {
        override fun add(num1: Int, num2: Int): Int {
            Log.d(TAG, "add($num1, $num2) called")
            return num1 + num2
        }

        override fun times(num1: Int, num2: Int): Int {
            Log.d(TAG, "times($num1, $num2) called")
            return num1 * num2
        }

        override fun sub(num1: Int, num2: Int): Int {
            Log.d(TAG, "sub($num1, $num2) called")
            return num1 - num2
        }

        override fun div(num1: Int, num2: Int): Int {
            Log.d(TAG, "div($num1, $num2) called")
            if(num2 == 0) return 0
            return num1/num2
        }
    }

    class BinderV2: ICalculatorContractV2.Stub() {
        override fun add(num1: Int, num2: Int): Int {
            Log.d(TAG, "add($num1, $num2) called on V2")
            return num1 + num2
        }

        override fun times(num1: Int, num2: Int): Int {
            Log.d(TAG, "times($num1, $num2) called on V2")
            return num1 * num2
        }

        override fun sub(num1: Int, num2: Int): Int {
            Log.d(TAG, "sub($num1, $num2) called on V2")
            return num1 - num2
        }

        override fun div(num1: Int, num2: Int, errorValue: Int): Int {
            Log.d(TAG, "div($num1, $num2) called on V2")
            if(num2 == 0) return errorValue
            return num1/num2
        }

        override fun mod(num1: Int, num2: Int): Int {
            Log.d(TAG, "mod($num1, $num2) called on V2")
            if(num2 == 0) return 0
            return num1%num2
        }
    }
}