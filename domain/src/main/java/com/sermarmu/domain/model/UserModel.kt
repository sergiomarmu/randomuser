package com.sermarmu.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.sermarmu.data.entity.User
import com.sermarmu.domain.model.UserModel.Companion.FEMALE
import com.sermarmu.domain.model.UserModel.Companion.MALE
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Parcelize
data class UserModel(
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
    get() = when (this.gender) {
        UserModel.Gender.FEMALE -> FEMALE
        UserModel.Gender.MALE -> MALE
    }

val UserModel.dateRegisteredFormat: String
    @SuppressLint("SimpleDateFormat")
    get() = OffsetDateTime.parse(this.registered.date)
        .format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )


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

suspend fun User.toUserModel() = UserModel(
    uuid = this.uuid,
    gender = UserModel.Gender.FEMALE,
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


suspend fun UserModel.toUser() = User(
    uuid = this.uuid,
    gender = User.Gender.FEMALE,
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
