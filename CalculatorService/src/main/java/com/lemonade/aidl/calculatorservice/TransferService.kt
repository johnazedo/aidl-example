package com.lemonade.aidl.calculatorservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel
import android.os.SharedMemory
import android.system.OsConstants
import android.util.Log
import com.lemonade.aidl.aidlcommon.transfer.ITransferContractV1
import com.lemonade.aidl.aidlcommon.transfer.TransferData

private const val TAG = "TransferService"

class TransferService: Service(){

    private val binder = Binder()
    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind")
        return binder
    }

    class Binder: ITransferContractV1.Stub() {
        override fun getData(numberOfData: Int): List<TransferData> {
            Log.d(TAG, "getData($numberOfData) called")
            return List(numberOfData) { TransferData() }
        }

        override fun getDataV2(numberOfData: Int): SharedMemory? {
            Log.d(TAG, "getDataV2($numberOfData) called for $numberOfData items")
            val dataList = List(numberOfData) { TransferData() }

            val parcel = Parcel.obtain()
            return try {
                // Write the list to a parcel to determine its size.
                parcel.writeTypedList(dataList)
                val dataSize = parcel.dataSize()
                Log.d(TAG, "Marshalled data size: $dataSize bytes")

                // Create a shared memory segment with the required size.
                val sharedMemory = SharedMemory.create("TransferData", dataSize)

                // Map the shared memory to get a writable ByteBuffer.
                val byteBuffer = sharedMemory.map(OsConstants.PROT_READ or OsConstants.PROT_WRITE, 0, dataSize)

                // Marshall the parcel and copy its data into the shared memory buffer.
                val bytes = parcel.marshall()
                byteBuffer.put(bytes)

                // Unmap from our address space before returning.
                SharedMemory.unmap(byteBuffer)

                // Set the protection to read-only for the client.
                sharedMemory.setProtect(OsConstants.PROT_READ)

                // The SharedMemory object is Parcelable and is sent via Binder.
                sharedMemory
            } catch (e: Exception) {
                Log.e(TAG, "Error creating shared memory", e)
                null
            } finally {
                parcel.recycle()
            }
        }
    }
}
