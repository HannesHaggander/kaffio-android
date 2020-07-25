package com.towerowl.kaffio.repositories

import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.data.daos.UserDao

class AuthenticationRepository(private val userDao: UserDao) {

    suspend fun getAuthenticationData(): User? {
        return userDao.getUser()
    }

    suspend fun insertAuthenticationInformation(user: User) {
        if (user.name.isEmpty()) throw Exception("User with empty username can not be inserted")

        userDao.insertUser(user)
    }

    suspend fun deleteAuthenticationInformation() {
        userDao.getUser()?.run { userDao.deleteUser(this) }
    }
}