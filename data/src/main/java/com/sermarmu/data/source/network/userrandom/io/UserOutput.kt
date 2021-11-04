package com.sermarmu.data.source.network.userrandom.io

import com.google.gson.annotations.SerializedName
import com.sermarmu.data.entity.User
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

data class UserOutput(
    @SerializedName("login")
    val login: Login,
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("name")
    val name: SurName,
    @SerializedName("location")
    val location: Location,
    @SerializedName("registered")
    val registered: Registered,
    @SerializedName("email")
    val email: String,
    @SerializedName("picture")
    val picture: Picture,
    @SerializedName("phone")
    val phone: String
) {
    data class Login(
        @SerializedName("uuid")
        val uuid: String
    )

    enum class Gender {
        @SerializedName("female")
        FEMALE,

        @SerializedName("male")
        MALE,
    }

    data class SurName(
        @SerializedName("first")
        val first: String,
        @SerializedName("last")
        val last: String
    )

    data class Location(
        @SerializedName("street")
        val street: Street,
        @SerializedName("city")
        val city: String,
        @SerializedName("state")
        val state: String
    ) {
        data class Street(
            @SerializedName("number")
            val number: Int,
            @SerializedName("name")
            val name: String,
        )
    }

    data class Registered(
        @SerializedName("date")
        val date: String
    )

    data class Picture(
        @SerializedName("large")
        val large: String
    )
}


suspend fun Iterable<UserOutput>.toUser() = asFlow().map {
    User(
        id = 0,
        uuid = it.login.uuid,
        gender = when (it.gender) {
            UserOutput.Gender.FEMALE ->
                User.Gender.FEMALE
            UserOutput.Gender.MALE ->
                User.Gender.MALE
        },
        name = User.SurName(
            first = it.name.first,
            last = it.name.last
        ),
        location = User.Location(
            street = User.Location.Street(
                number = it.location.street.number,
                name = it.location.street.name
            ),
            state = it.location.state,
            city = it.location.city
        ),
        registered = User.Registered(
            date = it.registered.date
        ),
        email = it.email,
        picture = User.Picture(
            large = it.picture.large
        ),
        phone = it.phone
    )
}.toList()