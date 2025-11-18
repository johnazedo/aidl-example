package com.lemonade.aidl.calculatorservice

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log

private const val TAG = "CalculatorServiceWithMessenger"

class CalculatorServiceWithMessenger : Service() {

    private val messenger by lazy {
        Messenger(getHandler(Looper.getMainLooper()))
    }

    private fun getHandler(looper: Looper): Handler {
        return CalculatorHandler(
            looper,
            CoroutineExecutor(),
            CalculatorUseCase()
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return messenger.binder
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
    }
}

class CalculatorUseCase {
    suspend fun add(numberOne: Int, numberTwo: Int): Int = numberOne + numberTwo
    suspend fun subtract(numberOne: Int, numberTwo: Int): Int = numberOne - numberTwo
    suspend fun multiply(numberOne: Int, numberTwo: Int): Int = numberOne * numberTwo
    suspend fun divide(numberOne: Int, numberTwo: Int): Int = if (numberTwo != 0) numberOne / numberTwo else 0
}

class CalculatorHandler(
    looper: Looper,
    private val executor: CoroutineExecutor,
    private val useCase: CalculatorUseCase
) : Handler(looper) {
    companion object {
        const val COMMAND_ADD = 0
        const val COMMAND_SUBTRACT = 1
        const val COMMAND_MULTIPLY = 2
        const val COMMAND_DIVIDE = 3

        const val RESULT = 4
    }

    override fun handleMessage(msg: Message) {
        val a = msg.data.getInt("number_one")
        val b = msg.data.getInt("number_two")
        val replyTo = msg.replyTo

        val onSuccess: (Int) -> Unit = { result ->
            val reply = Message.obtain(null, RESULT)
            reply.data.putInt("result", result)
            runCatching { replyTo.send(reply) }.onFailure {
                Log.e(TAG, "Failed to send reply", it)
            }
        }

        val onError: (Throwable) -> Unit = { error ->
            Log.e(TAG, "Operation failed", error)
        }

        when (msg.what) {
            COMMAND_ADD -> {
                executor.execute(
                    run = { useCase.add(a, b) },
                    onSuccess = onSuccess,
                    onError = onError
                )
            }
            COMMAND_SUBTRACT -> {
                executor.execute(
                    run = { useCase.subtract(a, b) },
                    onSuccess = onSuccess,
                    onError = onError
                )
            }
            COMMAND_MULTIPLY -> {
                executor.execute(
                    run = { useCase.multiply(a, b) },
                    onSuccess = onSuccess,
                    onError = onError
                )
            }
            COMMAND_DIVIDE -> {
                executor.execute(
                    run = { useCase.divide(a, b) },
                    onSuccess = onSuccess,
                    onError = onError
                )
            }
            else -> {
                Log.i(TAG, "Command not found: ${msg.what}")
            }
        }
    }
}