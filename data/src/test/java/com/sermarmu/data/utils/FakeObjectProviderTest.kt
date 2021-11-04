package com.sermarmu.data.utils

import com.sermarmu.data.entity.User
import com.sermarmu.data.source.network.userrandom.io.UserOutput
import com.sermarmu.domain.model.UserModel

class FakeObjectProviderTest {

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

        val fakeUserOutput = UserOutput(
            login = UserOutput.Login(
                uuid = "uuid"
            ),
            gender = UserOutput.Gender.FEMALE,
            name = UserOutput.SurName(
                first = "fakeFirst",
                last = "fakeLast"
            ),
            location = UserOutput.Location(
                street = UserOutput.Location.Street(
                    number = 0,
                    name = "fakeNumber"
                ),
                state = "fakeState",
                city = "fakeCity"
            ),
            registered = UserOutput.Registered(
                date = "fakeDate"
            ),
            email = "fake@gmauil.cum",
            picture = UserOutput.Picture(
                large = "fakeImage.jpeg"
            ),
            phone = "000000000"
        )
    }

}

