package com.lemonade.aidl.aidlcommon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

abstract class ServiceResult(
    private val error: ServiceError? = null
) {
    protected abstract fun getData(): Data?

    fun onSuccess(block: (Data) -> Unit): ServiceResult {
        if(error != null) return this
        val data: Data = getData() ?: return this
        block(data)
        return this
    }

    fun onError(block: (ServiceError) -> Unit): ServiceResult {
        error?.let { block(it) }
        return this
    }
}

interface Data{}

@Parcelize
class ServiceError(
    val message: String,
    val code: Int
): Parcelable

