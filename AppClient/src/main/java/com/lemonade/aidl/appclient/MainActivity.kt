package com.lemonade.aidl.appclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lemonade.aidlcommon.ICalculatorContractV1
import com.lemonade.aidlcommon.ICalculatorContractV2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    var calculatorServiceV2 by remember { mutableStateOf<ICalculatorContractV2?>(null) }
    var calculatorServiceV1 by remember { mutableStateOf<ICalculatorContractV1?>(null) }
    val context = LocalContext.current

    val serviceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                when (service?.interfaceDescriptor) {
                    ICalculatorContractV2.DESCRIPTOR -> {
                        calculatorServiceV2 = ICalculatorContractV2.Stub.asInterface(service)
                    }
                    ICalculatorContractV1.DESCRIPTOR -> {
                        calculatorServiceV1 = ICalculatorContractV1.Stub.asInterface(service)
                    }
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                calculatorServiceV2 = null
                calculatorServiceV1 = null
            }
        }
    }

    DisposableEffect(Unit) {
        val intent = Intent("com.lemonade.aidl.calculatorservice.CALCULATOR_SERVICE")
        intent.setPackage("com.lemonade.aidl.calculatorservice")
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        onDispose {
            context.unbindService(serviceConnection)
        }
    }

    MaterialTheme {
        Scaffold { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                item {
                    var number1 by rememberoverride fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                when (service?.interfaceDescriptor) {
                    ICalculatorContractV2.DESCRIPTOR -> {
                        calculatorServiceV2 = ICalculatorContractV2.Stub.asInterface(service)
                    }
                    ICalculatorContractV1.DESCRIPTOR -> {
                        calculatorServiceV1 = ICalculatorContractV1.Stub.asInterface(service)
                    }
                }
            } { mutableStateOf("") }
                    var number2 by remember { mutableStateOf("") }
                    var result by remember { mutableStateOf("") }

                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = number1,
                            onValueChange = { number1 = it },
                            label = { Text("Primeiro número") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = number2,
                            onValueChange = { number2 = it },
                            label = { Text("Segundo número") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                val num1 = number1.toIntOrNull() ?: 0
                                val num2 = number2.toIntOrNull() ?: 0
                                result = (calculatorServiceV2?.add(num1, num2)?.toString() ?: calculatorServiceV1?.add(num1, num2)?.toString()) ?: "N/A"
                            }) {
                                Text("+")
                            }
                            Button(onClick = {
                                val num1 = number1.toIntOrNull() ?: 0
                                val num2 = number2.toIntOrNull() ?: 0
                                result = (calculatorServiceV2?.sub(num1, num2)?.toString() ?: calculatorServiceV1?.sub(num1, num2)?.toString()) ?: "N/A"
                            }) {
                                Text("-")
                            }
                            Button(onClick = {
                                val num1 = number1.toIntOrNull() ?: 0
                                val num2 = number2.toIntOrNull() ?: 0
                                result = (calculatorServiceV2?.times(num1, num2)?.toString() ?: calculatorServiceV1?.times(num1, num2)?.toString()) ?: "N/A"
                            }) {
                                Text("*")
                            }
                            Button(onClick = {
                                val num1 = number1.toIntOrNull() ?: 0
                                val num2 = number2.toIntOrNull() ?: 0
                                result = try {
                                    (calculatorServiceV2?.div(num1, num2, 0)?.toString() ?: calculatorServiceV1?.div(num1, num2)?.toString()) ?: "N/A"
                                } catch (e: Exception) {
                                    "Error"
                                }
                            }) {
                                Text("/")
                            }
                        }
                        Text(text = "Resultado: $result", modifier = Modifier.padding(top = 16.dp))
                    }
                }
            }
        }
    }
}
