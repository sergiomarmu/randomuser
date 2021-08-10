package com.sermarmu.domain.utils;

import com.sermarmu.domain.model.UserModel

class FakeObjectProviderTest {

    companion object {
        val fakeUserModel = UserModel(
                id = 0,
                uuid = "uuid",
                gender = UserModel.Gender.MALE,
                name = UserModel.SurName(
                        first = "fakeFirst",
                        last = "fakeLast"
                ),
                location = UserModel.Location(
                        street = UserModel.Location.Street(
                                number = 0,
                                name = "fakeNumber"
                        ),
                        state = "fakeState",
                        city = "fakeCity"
                ),
                registered = UserModel.Registered(
                        date = "fakeDate"
                ),
                email = "fake@gmauil.cum",
                picture = UserModel.Picture(
                        large = "fakeImage.jpeg"
                ),
                phone = "000000000"
        )
    }
}


