package com.lemonade.aidl.appclient

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.os.ParcelFileDescriptor
import android.os.SharedMemory
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV1
import com.lemonade.aidl.aidlcommon.calculator.ICalculatorContractV2
import com.lemonade.aidl.aidlcommon.transfer.ITransferContractV1
import com.lemonade.aidl.aidlcommon.transfer.TransferData
import java.io.FileInputStream
import kotlin.system.measureTimeMillis

private const val TAG = "MainActivity"

sealed class Screen {
    object Selection : Screen()
    object Calculator : Screen()
    object Transfer : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContent { App() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
    Log.d(TAG, "App")
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Selection) }

    MaterialTheme {
        Scaffold {
            when (currentScreen) {
                Screen.Selection -> SelectionScreen(
                    onCalculatorClick = { currentScreen = Screen.Calculator },
                    onTransferClick = { currentScreen = Screen.Transfer }
                )
                Screen.Calculator -> CalculatorScreen()
                Screen.Transfer -> TransferScreen()
            }
        }
    }
}

@Composable
fun SelectionScreen(onCalculatorClick: () -> Unit, onTransferClick: () -> Unit) {
    Log.d(TAG, "SelectionScreen")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            Log.d(TAG, "Calculator Service button clicked")
            onCalculatorClick()
        }) {
            Text("Calculator Service")
        }
        Button(onClick = {
            Log.d(TAG, "File Transfer Service button clicked")
            onTransferClick()
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text("File Transfer Service")
        }
    }
}

@Composable
fun CalculatorScreen() {
    Log.d(TAG, "CalculatorScreen")
    var calculatorFeatures by remember { mutableStateOf<CalculatorFeatures?>(null) }
    val context = LocalContext.current

    val serviceConnection = remember {
        ServiceManager({ result ->
            result.onSuccess { feature ->
                calculatorFeatures = feature
            }
        })
    }

    DisposableEffect(Unit) {
        Log.d(TAG, "Binding to CalculatorService")
        val intent = Intent("com.lemonade.aidl.calculatorservice.CALCULATOR_SERVICE")
        intent.setPackage("com.lemonade.aidl.calculatorservice")
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        onDispose {
            Log.d(TAG, "Unbinding from CalculatorService")
            context.unbindService(serviceConnection)
        }
    }

    var number1 by remember { mutableStateOf("") }
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
                calculatorFeatures?.add(num1, num2)?.onSuccess{ result = it.toString()}
            }) {
                Text("+")
            }
            Button(onClick = {
                val num1 = number1.toIntOrNull() ?: 0
                val num2 = number2.toIntOrNull() ?: 0
                calculatorFeatures?.sub(num1, num2)?.onSuccess{ result = it.toString()}
            }) {
                Text("-")
            }
            Button(onClick = {
                val num1 = number1.toIntOrNull() ?: 0
                val num2 = number2.toIntOrNull() ?: 0
                calculatorFeatures?.times(num1, num2)?.onSuccess{ result = it.toString()}
            }) {
                Text("*")
            }
            Button(onClick = {
                val num1 = number1.toIntOrNull() ?: 0
                val num2 = number2.toIntOrNull() ?: 0
                calculatorFeatures?.div(num1, num2)?.onSuccess{ result = it.toString()}
            }) {
                Text("/")
            }
        }
        Text(text = "Resultado: $result", modifier = Modifier.padding(top = 16.dp))
    }
}
@Composable
fun TransferScreen() {
    Log.d(TAG, "TransferScreen")
    var transferService by remember { mutableStateOf<ITransferContractV1?>(null) }
    val context = LocalContext.current

    val serviceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(TAG, "Transfer service connected")
                transferService = ITransferContractV1.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(TAG, "Transfer service disconnected")
                transferService = null
            }
        }
    }

    DisposableEffect(Unit) {
        Log.d(TAG, "Binding to TransferService")
        val intent = Intent("com.lemonade.aidl.calculatorservice.TRANSFER_SERVICE")
        intent.setPackage("com.lemonade.aidl.calculatorservice")
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        onDispose {
            Log.d(TAG, "Unbinding from TransferService")
            context.unbindService(serviceConnection)
        }
    }

    var numberOfItems by remember { mutableStateOf("1") }
    var results by remember { mutableStateOf<List<String>>(emptyList()) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            OutlinedTextField(
                value = numberOfItems,
                onValueChange = { numberOfItems = it },
                label = { Text("Number of items to fetch") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    val count = numberOfItems.toIntOrNull() ?: 0
                    Log.d(TAG, "Get Data (List) button clicked with count: $count")
                    if (count > 0 && transferService != null) {
                        var dataSize = 0
                        val time = measureTimeMillis {
                            try {
                                val data = transferService?.getData(count)
                                dataSize = data?.size ?: 0
                            } catch (e: Exception) {
                                Log.e(TAG, "getData failed (likely too large)", e)
                                dataSize = -1 // Error indicator
                            }
                        }
                        val newResult = if (dataSize != -1) {
                            "List: Got $dataSize items. Call took ${time}ms."
                        } else {
                            "List: Transaction FAILED. Call took ${time}ms."
                        }
                        results = listOf(newResult) + results // Prepend to list
                        Log.d(TAG, newResult)
                    }
                }) {
                    Text("Get Data (List)")
                }
                Button(onClick = {
                    fetchWithSharedMemory(transferService, numberOfItems, results) { newResults ->
                        results = newResults
                    }
                }) {
                    Text("Get Data (SharedMem)")
                }
                Button(onClick = {
                    fetchWithMemoryFile(transferService, numberOfItems, results) { newResults ->
                        results = newResults
                    }
                }) {
                    Text("Get Data (MemoryFile)")
                }
            }
        }

        items(results) { result ->
            Text(
                text = result,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


private fun fetchWithSharedMemory(
    transferService: ITransferContractV1?,
    numberOfItems: String,
    currentResults: List<String>,
    updateResults: (List<String>) -> Unit
) {
    val count = numberOfItems.toIntOrNull() ?: 0
    Log.d(TAG, "Get Data (SharedMemory) button clicked with count: $count")
    if (count > 0 && transferService != null) {
        var dataSize = 0
        val time = measureTimeMillis {
            val sharedMemory = transferService.getDataV2(count)
            if (sharedMemory != null) {
                try {
                    val byteBuffer = sharedMemory.mapReadOnly()
                    val parcel = Parcel.obtain()
                    try {
                        val bytes = ByteArray(byteBuffer.remaining())
                        byteBuffer.get(bytes)
                        parcel.unmarshall(bytes, 0, bytes.size)
                        parcel.setDataPosition(0)
                        // This is crucial for custom Parcelables
//                        parcel.readBundle(TransferData::class.java.classLoader)
//                        parcel.setDataPosition(0)
                        val data: ArrayList<TransferData>? =
                            parcel.createTypedArrayList(TransferData.CREATOR)
                        dataSize = data?.size ?: 0
                    } finally {
                        parcel.recycle()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to read from SharedMemory", e)
                    dataSize = -1
                } finally {
                    sharedMemory.close()
                }
            }
        }
        val newResult = if (dataSize != -1) {
            "SharedMem: Got $dataSize items. Call took ${time}ms."
        } else {
            "SharedMem: FAILED to read data. Call took ${time}ms."
        }
        updateResults(listOf(newResult) + currentResults)
        Log.d(TAG, newResult)
    }
}


private fun fetchWithMemoryFile(
    transferService: ITransferContractV1?,
    numberOfItems: String,
    currentResults: List<String>,
    updateResults: (List<String>) -> Unit
) {
    val count = numberOfItems.toIntOrNull() ?: 0
    Log.d(TAG, "Get Data (MemoryFile) button clicked with count: $count")
    if (count > 0 && transferService != null) {
        var dataSize = 0
        val time = measureTimeMillis {
            var pfd: ParcelFileDescriptor? = null
            try {
                pfd = transferService.getDataV3(count)
                if (pfd != null) {
                    // 1. Get an InputStream from the ParcelFileDescriptor
                    FileInputStream(pfd.fileDescriptor).use { fis ->
                        val bytes = fis.readBytes()
                        val parcel = Parcel.obtain()
                        try {
                            // 2. Unmarshall the bytes into the Parcel
                            parcel.unmarshall(bytes, 0, bytes.size)
                            parcel.setDataPosition(0)
                            // 4. Create the typed list from the parcel
                            val data: ArrayList<TransferData>? =
                                parcel.createTypedArrayList(TransferData.CREATOR)
                            dataSize = data?.size ?: 0
                        } finally {
                            parcel.recycle()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to read from MemoryFile", e)
                dataSize = -1
            } finally {
                pfd?.close()
            }
        }
        val newResult = if (dataSize != -1) {
            "MemoryFile: Got $dataSize items. Call took ${time}ms."
        } else {
            "MemoryFile: FAILED to read data. Call took ${time}ms."
        }
        updateResults(listOf(newResult) + currentResults)
        Log.d(TAG, newResult)
    }
}
