package com.lemonade.aidl.aidlcommon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nums(
    private val num1: Int,
    private val num2: Int
): Parcelable

@Parcelize
class CalculatorServiceResult(
    val result: Int
): ServiceResult(), Data, Parcelable {
    override fun getData(): CalculatorServiceResult {
        return this
    }
}
