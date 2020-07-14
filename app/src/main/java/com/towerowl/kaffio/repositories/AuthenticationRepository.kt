package com.towerowl.kaffio.repositories

import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.data.daos.UserDao

class AuthenticationRepository(private val userDao: UserDao) {

    fun getAuthenticationData(): User? {
        return userDao.getUser()
    }

    fun insertAuthenticationInformation(user: User) {
        if (user.name.isEmpty()) throw Exception("Username has to not be empty for insertion")

        userDao.insertUser(user)
    }

    fun deleteAuthenticationInformation() {
        userDao.getUser()?.run { userDao.deleteUser(this) }
    }
}