package com.lemonade.aidl.aidlcommon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Numbers(
    private val num1: Int,
    private val num2: Int
): Parcelable


@Parcelize
class CalculatorServiceResult(
    val operationResult: Int? = null
): ServiceResult(), Parcelable
