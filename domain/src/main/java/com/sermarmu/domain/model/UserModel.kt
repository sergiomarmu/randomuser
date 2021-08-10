package com.sermarmu.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.sermarmu.domain.model.UserModel.Companion.FEMALE
import com.sermarmu.domain.model.UserModel.Companion.MALE
import kotlinx.android.parcel.Parcelize
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Parcelize
data class UserModel(
    val id: Int,
    val uuid: String,
    val gender: Gender,
    val name: SurName,
    val location: Location,
    val registered: Registered,
    val email: String,
    val picture: Picture,
    val phone: String
) : Parcelable {

    companion object {
        const val FEMALE = "Female"
        const val MALE = "Male"
    }

    enum class Gender {
        FEMALE,
        MALE,
    }

    @Parcelize
    data class SurName(
        val first: String,
        val last: String
    ) : Parcelable

    @Parcelize
    data class Location(
        val street: Street,
        val city: String,
        val state: String
    ) : Parcelable {
        @Parcelize
        data class Street(
            val number: Int,
            val name: String,
        ) : Parcelable
    }

    @Parcelize
    data class Registered(
        val date: String
    ) : Parcelable

    @Parcelize
    data class Picture(
        val large: String
    ) : Parcelable
}

val UserModel.genderFormat: String
    get() = when (gender) {
        UserModel.Gender.FEMALE -> FEMALE
        UserModel.Gender.MALE -> MALE
    }

val UserModel.dateRegisteredFormat: String
    @SuppressLint("SimpleDateFormat")
    get() = OffsetDateTime.parse(registered.date)
        .format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )

val UserModel.streetFormat: String
    get() = "${location.street.number} ${location.street.name}"
