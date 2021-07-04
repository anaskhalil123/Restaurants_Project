package com.a.khalil.restaurants_project.models

import android.os.Parcel
import android.os.Parcelable

data class DeatailsMeals(
    var image: Int, var name: String?, var info: String?,
    var price: Int, var react: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(name)
        parcel.writeString(info)
        parcel.writeInt(price)
        parcel.writeInt(react)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeatailsMeals> {
        override fun createFromParcel(parcel: Parcel): DeatailsMeals {
            return DeatailsMeals(parcel)
        }

        override fun newArray(size: Int): Array<DeatailsMeals?> {
            return arrayOfNulls(size)
        }
    }
}