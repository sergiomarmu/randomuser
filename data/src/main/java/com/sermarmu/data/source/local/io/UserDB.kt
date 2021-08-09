package com.sermarmu.data.source.local.io


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sermarmu.data.entity.User
import com.sermarmu.data.source.local.io.UserDB.Companion.FEMALE
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

@Entity(tableName = "users")
data class UserDB(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int = 0,
    @field:SerializedName("uuid")
    val uuid: String,
    @field:SerializedName("gender")
    val gender: String,
    @field:SerializedName("firstName")
    val firstName: String,
    @field:SerializedName("lastName")
    val lastName: String,
    @field:SerializedName("street_name")
    val streetName: String,
    @field:SerializedName("street_number")
    val streetNumber: Int,
    @field:SerializedName("city")
    val city: String,
    @field:SerializedName("state")
    val state: String,
    @field:SerializedName("dateRegistered")
    val dateRegistered: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("picture")
    val picture: String,
    @field:SerializedName("phone")
    val phone: String
) {
    companion object {
        const val FEMALE = "female"
        const val MALE = "male"
    }
}

suspend fun Iterable<UserDB>.toUser() = asFlow().map {
    User(
        uuid = it.uuid,
        gender = if (it.gender == FEMALE)
            User.Gender.FEMALE
        else User.Gender.MALE,
        name = User.SurName(
            first = it.firstName,
            last = it.lastName
        ),
        location = User.Location(
            street = User.Location.Street(
                number = it.streetNumber,
                name = it.streetName
            ),
            state = it.state,
            city = it.city
        ),
        registered = User.Registered(
            date = it.dateRegistered
        ),
        email = it.email,
        picture = User.Picture(
            large = it.picture
        ),
        phone = it.phone
    )
}.toList()