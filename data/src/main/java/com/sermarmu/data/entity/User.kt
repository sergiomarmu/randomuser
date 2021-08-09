package com.sermarmu.data.entity

import com.sermarmu.data.source.local.io.UserDB
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

data class User(
    val id: Int,
    val uuid: String,
    val gender: Gender,
    val name: SurName,
    val location: Location,
    val registered: Registered,
    val email: String,
    val picture: Picture,
    val phone: String
) {
    enum class Gender {
        FEMALE,
        MALE,
    }

    data class SurName(
        val first: String,
        val last: String
    )

    data class Location(
        val street: Street,
        val city: String,
        val state: String
    ) {
        data class Street(
            val number: Int,
            val name: String,
        )
    }

    data class Registered(
        val date: String
    )

    data class Picture(
        val large: String
    )
}


suspend fun Iterable<User>.toUserDB() = asFlow().map {
    UserDB(
        id = it.id,
        uuid = it.uuid,
        gender = when (it.gender) {
            User.Gender.FEMALE ->
                UserDB.FEMALE
            User.Gender.MALE ->
                UserDB.MALE
        },
        firstName = it.name.first,
        lastName = it.name.last,
        streetNumber = it.location.street.number,
        streetName = it.location.street.name,
        state = it.location.state,
        city = it.location.city,
        dateRegistered = it.registered.date,
        email = it.email,
        picture = it.picture.large,
        phone = it.phone
    )
}.toList()

suspend fun User.toUserDB() = UserDB(
    id = this.id,
    uuid = this.uuid,
    gender = when (this.gender) {
        User.Gender.FEMALE ->
            UserDB.FEMALE
        User.Gender.MALE ->
            UserDB.MALE
    },
    firstName = this.name.first,
    lastName = this.name.last,
    streetNumber = this.location.street.number,
    streetName = this.location.street.name,
    state = this.location.state,
    city = this.location.city,
    dateRegistered = this.registered.date,
    email = this.email,
    picture = this.picture.large,
    phone = this.phone
)


