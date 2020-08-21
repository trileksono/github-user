package com.tleksono.githubuser.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by trileksono on 18/08/20
 */
data class User(
    val avatarUrl: String,
    val id: Int,
    val login: String,
    val url: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString().orEmpty(),
        source.readInt(),
        source.readString().orEmpty(),
        source.readString().orEmpty()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(avatarUrl)
        writeInt(id)
        writeString(login)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}
