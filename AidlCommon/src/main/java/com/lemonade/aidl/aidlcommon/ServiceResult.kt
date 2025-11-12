package com.lemonade.aidl.aidlcommon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

abstract class ServiceResult(
    private val error: ServiceError? = null
)

@Parcelize
class ServiceError(
    val message: String,
    val code: Int
): Parcelable

