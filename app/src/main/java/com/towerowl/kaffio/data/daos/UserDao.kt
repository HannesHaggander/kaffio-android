package com.towerowl.kaffio.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.towerowl.kaffio.data.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): User?

}