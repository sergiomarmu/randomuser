package com.sermarmu.data.utils

import com.sermarmu.data.entity.User
import com.sermarmu.domain.model.UserModel

class FakeObjectProviderAndroidTest {

    companion object {
        val fakeUser = User(
            id = 0,
            uuid = "uuid",
            gender = User.Gender.MALE,
            name = User.SurName(
                first = "fakeFirst",
                last = "fakeLast"
            ),
            location = User.Location(
                street = User.Location.Street(
                    number = 0,
                    name = "fakeNumber"
                ),
                state = "fakeState",
                city = "fakeCity"
            ),
            registered = User.Registered(
                date = "fakeDate"
            ),
            email = "fake@gmauil.cum",
            picture = User.Picture(
                large = "fakeImage.jpeg"
            ),
            phone = "000000000"
        )
    }

}

