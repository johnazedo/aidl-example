package com.lemonade.aidl.calculatorservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.getValue

class CalculatorServiceWithMessenger: Service(){

    private val messenger by lazy<Messenger> {
        Messenger(getHandler(Looper.getMainLooper()))
    }

    fun getHandler(looper: Looper): Handler {
        return CalculatorHandler(looper)
    }

    override fun onBind(intent: Intent?): IBinder? {
       return messenger.binder
    }
}


class CalculatorHandler(looper: Looper): Handler(looper) {
    companion object {
        const val COMMAND_ADD = 0
        const val COMMAND_SUBTRACT = 1
        const val COMMAND_MULTIPLY = 2
        const val COMMAND_DIVIDE = 3

        const val RESULT = 4
    }

    override fun handleMessage(msg: Message) {
        when(msg.what) {
            COMMAND_ADD -> {
                val number_one = msg.data.getInt("number_one")
                val number_two = msg.data.getInt("number_two")

                CoroutineScope(Dispatchers.IO).launch {
                    val result = number_one + number_two

                    withContext(Dispatchers.Main) {
                        val reply = Message.obtain(null, RESULT)
                        reply.data.putInt("result", result)
                        msg.replyTo.send(reply)
                    }
                }
            }
            else -> {
                val reply = Message.obtain(null, RESULT)
                reply.data.putString("error", "Commando não válido")
            }
        }
    }
}