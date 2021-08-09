package com.sermarmu.data.entity

data class User(
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