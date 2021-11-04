package com.sermarmu.data.entity

import com.sermarmu.data.source.local.room.io.UserDB
import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
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

suspend fun Iterable<User>.toUserModel() = asFlow().map {
    UserModel(
        id = it.id,
        uuid = it.uuid,
        gender = when (it.gender) {
            User.Gender.FEMALE -> UserModel.Gender.FEMALE
            User.Gender.MALE -> UserModel.Gender.MALE
        },
        name = UserModel.SurName(
            first = it.name.first,
            last = it.name.last
        ),
        location = UserModel.Location(
            street = UserModel.Location.Street(
                number = it.location.street.number,
                name = it.location.street.name
            ),
            state = it.location.state,
            city = it.location.city
        ),
        registered = UserModel.Registered(
            date = it.registered.date
        ),
        email = it.email,
        picture = UserModel.Picture(
            large = it.picture.large
        ),
        phone = it.phone
    )
}.toList()

fun User.toUserModel() = UserModel(
    id = this.id,
    uuid = this.uuid,
    gender = when (this.gender) {
        User.Gender.FEMALE -> UserModel.Gender.FEMALE
        User.Gender.MALE -> UserModel.Gender.MALE
    },
    name = UserModel.SurName(
        first = this.name.first,
        last = this.name.last
    ),
    location = UserModel.Location(
        street = UserModel.Location.Street(
            number = this.location.street.number,
            name = this.location.street.name
        ),
        state = this.location.state,
        city = this.location.city
    ),
    registered = UserModel.Registered(
        date = this.registered.date
    ),
    email = this.email,
    picture = UserModel.Picture(
        large = this.picture.large
    ),
    phone = this.phone
)

suspend fun Iterable<UserModel>.toUser() = asFlow().map {
    User(
        id = it.id,
        uuid = it.uuid,
        gender = when (it.gender) {
            UserModel.Gender.FEMALE -> User.Gender.FEMALE
            UserModel.Gender.MALE -> User.Gender.MALE
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


fun UserModel.toUser() = User(
    id = this.id,
    uuid = this.uuid,
    gender = when (this.gender) {
        UserModel.Gender.FEMALE -> User.Gender.FEMALE
        UserModel.Gender.MALE -> User.Gender.MALE
    },
    name = User.SurName(
        first = this.name.first,
        last = this.name.last
    ),
    location = User.Location(
        street = User.Location.Street(
            number = this.location.street.number,
            name = this.location.street.name
        ),
        state = this.location.state,
        city = this.location.city
    ),
    registered = User.Registered(
        date = this.registered.date
    ),
    email = this.email,
    picture = User.Picture(
        large = this.picture.large
    ),
    phone = this.phone
)


