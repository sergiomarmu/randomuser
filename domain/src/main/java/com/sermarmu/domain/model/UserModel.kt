package com.sermarmu.domain.model


import com.sermarmu.data.entity.User
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

data class UserModel(
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


suspend fun Iterable<User>.toUserModel() = asFlow().map {

    UserModel(
        uuid = it.uuid,
        gender = UserModel.Gender.FEMALE,
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

suspend fun Iterable<UserModel>.toUser() = asFlow().map {
    User(
        uuid = it.uuid,
        gender = User.Gender.FEMALE,
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