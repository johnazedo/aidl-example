package com.lemonade.aidl.calculatorservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.MemoryFile
import android.os.Parcel
import android.os.ParcelFileDescriptor
import android.os.SharedMemory
import android.system.OsConstants
import android.util.Log
import com.lemonade.aidl.aidlcommon.transfer.ITransferContractV1
import com.lemonade.aidl.aidlcommon.transfer.TransferData
import java.io.FileDescriptor
import java.io.IOException

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

        override fun getDataV3(numberOfData: Int): ParcelFileDescriptor? {
            Log.d(TAG, "getDataV3($numberOfData) called for $numberOfData items")
            val dataList = List(numberOfData) { TransferData() }

            val parcel = Parcel.obtain()
            var memoryFile: MemoryFile? = null
            return try {
                // 1. Marshall the list into a byte array using a Parcel.
                parcel.writeTypedList(dataList)
                val bytes = parcel.marshall()
                Log.d(TAG, "Marshalled data size for MemoryFile: ${bytes.size} bytes")

                // 2. Create a MemoryFile and write the byte array to it.
                // The name is optional and used for debugging.
                memoryFile = MemoryFile("transfer_data_mf", bytes.size)
                memoryFile.writeBytes(bytes, 0, 0, bytes.size)

                // 3. Get the FileDescriptor from the MemoryFile. This is the key to sharing.
                val getFileDescriptorMethod = MemoryFile::class.java.getDeclaredMethod("getFileDescriptor")
                val fd = getFileDescriptorMethod.invoke(memoryFile) as FileDescriptor


                // 4. Wrap the FileDescriptor in a ParcelFileDescriptor to send over Binder.
                // The remote process takes ownership of the duplicated descriptor.
                ParcelFileDescriptor.dup(fd)

            } catch (e: IOException) {
                Log.e(TAG, "Error creating MemoryFile", e)
                null
            } finally {
                parcel.recycle()
                // IMPORTANT: Do NOT close the MemoryFile here.
                // The Binder framework and the remote process are now responsible for the
                // lifecycle of the duplicated file descriptor. Closing it here would
                // cause a "bad file descriptor" error on the client side.
                // memoryFile?.close() // <-- DO NOT DO THIS
            }
        }
    }
}
