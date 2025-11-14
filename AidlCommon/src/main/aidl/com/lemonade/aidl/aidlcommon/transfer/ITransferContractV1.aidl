package com.lemonade.aidl.aidlcommon.transfer;

import com.lemonade.aidl.aidlcommon.transfer.TransferData;
import android.os.SharedMemory;
import android.os.ParcelFileDescriptor;

parcelable TransferData;

interface ITransferContractV1 {
    List<TransferData> getData(int numberOfData);
    SharedMemory getDataV2(int numberOfData);
    ParcelFileDescriptor getDataV3(int numberOfData);
}