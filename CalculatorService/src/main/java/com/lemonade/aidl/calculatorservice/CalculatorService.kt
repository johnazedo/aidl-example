package com.lemonade.aidl.calculatorservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.lemonade.aidlcommon.ICalculatorContractV1

class CalculatorService: Service() {
    override fun onBind(intent: Intent?): IBinder {
        Log.i("AIDL TEST", "OnBinding")
        return Binder()
    }

    class Binder: ICalculatorContractV1.Stub() {
        override fun add(num1: Int, num2: Int): Int {
            Log.i("AIDL TEST", "Call sum")
            return num1 + num2
        }

        override fun times(num1: Int, num2: Int): Int {
            Log.i("AIDL TEST", "Call times")
            return num1 * num2
        }

        override fun sub(num1: Int, num2: Int): Int {
            Log.i("AIDL TEST", "Call sub")
            return num1 - num2
        }

        override fun div(num1: Int, num2: Int): Int {
            Log.i("AIDL TEST", "Call div")
            if(num2 == 0) return 0
            return num1/num2
        }
    }
}