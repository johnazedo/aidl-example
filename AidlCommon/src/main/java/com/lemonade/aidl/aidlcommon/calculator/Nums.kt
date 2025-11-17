package com.lemonade.aidl.aidlcommon.calculator

import android.os.Parcelable
import com.lemonade.aidl.aidlcommon.ServiceResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class Numbers(
    val num1: Int,
    val num2: Int
): Parcelable


@Parcelize
class CalculatorServiceResult(
    val operationResult: Int? = null
): ServiceResult(), Parcelable
