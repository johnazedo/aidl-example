package com.lemonade.aidl.aidlcommon.transfer

import android.os.Parcel
import android.os.Parcelable

// Each object is 32KB
const val size = 75

data class TransferData(
    val field1: String,
    val field2: String,
    val field3: String,
    val field4: String,
    val field5: String,
    val field6: String,
    val field7: String,
    val field8: String,
    val field9: String,
    val field10: String,
    val field11: String,
    val field12: String,
    val field13: String,
    val field14: String,
    val field15: String,
    val field16: String,
    val field17: String,
    val field18: String,
    val field19: String,
    val field20: String,
    val field21: String,
    val field22: String,
    val field23: String,
    val field24: String,
    val field25: String,
    val field26: String,
    val field27: String,
    val field28: String,
    val field29: String,
    val field30: String,
    val field31: String,
    val field32: String,
    val field33: String,
    val field34: String,
    val field35: String,
    val field36: String,
    val field37: String,
    val field38: String,
    val field39: String,
    val field40: String,
    val field41: String,
    val field42: String,
    val field43: String,
    val field44: String,
    val field45: String,
    val field46: String,
    val field47: String,
    val field48: String,
    val field49: String,
    val field50: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!, parcel.readString()!!, parcel.readString()!!,
        parcel.readString()!!, parcel.readString()!!

    )

    constructor() : this(
        "a".repeat(size), "b".repeat(size), "c".repeat(size), "d".repeat(size),
        "e".repeat(size), "f".repeat(size), "g".repeat(size), "h".repeat(size),
        "i".repeat(size), "j".repeat(size), "k".repeat(size), "l".repeat(size),
        "m".repeat(size), "n".repeat(size), "o".repeat(size), "p".repeat(size),
        "q".repeat(size), "r".repeat(size), "s".repeat(size), "t".repeat(size),
        "u".repeat(size), "v".repeat(size), "w".repeat(size), "x".repeat(size),
        "y".repeat(size), "z".repeat(size), "A".repeat(size), "B".repeat(size),
        "C".repeat(size), "D".repeat(size), "E".repeat(size), "F".repeat(size),
        "C".repeat(size), "D".repeat(size), "E".repeat(size), "F".repeat(size),
        "C".repeat(size), "D".repeat(size), "E".repeat(size), "F".repeat(size),
        "C".repeat(size), "D".repeat(size), "E".repeat(size), "F".repeat(size),
        "C".repeat(size), "D".repeat(size), "E".repeat(size), "F".repeat(size),
        "C".repeat(size), "D".repeat(size)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(field1)
        parcel.writeString(field2)
        parcel.writeString(field3)
        parcel.writeString(field4)
        parcel.writeString(field5)
        parcel.writeString(field6)
        parcel.writeString(field7)
        parcel.writeString(field8)
        parcel.writeString(field9)
        parcel.writeString(field10)
        parcel.writeString(field11)
        parcel.writeString(field12)
        parcel.writeString(field13)
        parcel.writeString(field14)
        parcel.writeString(field15)
        parcel.writeString(field16)
        parcel.writeString(field17)
        parcel.writeString(field18)
        parcel.writeString(field19)
        parcel.writeString(field20)
        parcel.writeString(field21)
        parcel.writeString(field22)
        parcel.writeString(field23)
        parcel.writeString(field24)
        parcel.writeString(field25)
        parcel.writeString(field26)
        parcel.writeString(field27)
        parcel.writeString(field28)
        parcel.writeString(field29)
        parcel.writeString(field30)
        parcel.writeString(field31)
        parcel.writeString(field32)
        parcel.writeString(field33)
        parcel.writeString(field34)
        parcel.writeString(field35)
        parcel.writeString(field36)
        parcel.writeString(field37)
        parcel.writeString(field38)
        parcel.writeString(field39)
        parcel.writeString(field40)
        parcel.writeString(field41)
        parcel.writeString(field42)
        parcel.writeString(field43)
        parcel.writeString(field44)
        parcel.writeString(field45)
        parcel.writeString(field46)
        parcel.writeString(field47)
        parcel.writeString(field48)
        parcel.writeString(field49)
        parcel.writeString(field50)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransferData> {
        override fun createFromParcel(parcel: Parcel): TransferData {
            return TransferData(parcel)
        }

        override fun newArray(size: Int): Array<TransferData?> {
            return arrayOfNulls(size)
        }
    }
}