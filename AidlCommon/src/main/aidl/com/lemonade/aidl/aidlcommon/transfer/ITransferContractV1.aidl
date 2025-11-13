package com.lemonade.aidl.aidlcommon.transfer;

import com.lemonade.aidl.aidlcommon.transfer.TransferData;

parcelable TransferData;

interface ITransferContractV1 {
    List<TransferData> getData(int numberOfData);
}