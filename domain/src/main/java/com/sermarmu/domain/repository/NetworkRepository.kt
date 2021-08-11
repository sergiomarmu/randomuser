package com.sermarmu.domain.repository

import com.sermarmu.domain.model.UserModel

interface NetworkRepository {
    suspend fun retrieveUsers(): List<UserModel>
}