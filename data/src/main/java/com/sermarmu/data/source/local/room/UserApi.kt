package com.sermarmu.data.source.local.room

import androidx.room.*
import com.sermarmu.data.source.local.room.io.UserDB
import kotlinx.coroutines.flow.Flow

@Dao
interface UserApi {

    // region users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(
        users: List<UserDB>
    )

    @Query("SELECT * FROM users ")
    suspend fun retrieveAllUsers(): List<UserDB>

    @Query("SELECT * FROM users WHERE firstName LIKE :query")
    suspend fun findUserByName(
        query: String
    ): List<UserDB>


    @Query("DELETE FROM users")
    suspend fun clearAllUsers()

    @Delete
    suspend fun deleteUsers(
        user: UserDB
    )
    // endregion users

}