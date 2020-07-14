package com.towerowl.kaffio.repositories

import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.data.daos.UserDao

interface IAuthenticationRepository {
    fun getAuthenticationData(): User?

    fun insertAuthenticationInformation(user: User)

    fun deleteAuthenticationInformation()
}

class AuthenticationRepository(private val userDao: UserDao) : IAuthenticationRepository {

    override fun getAuthenticationData(): User? {
        return userDao.getUser()
    }

    override fun insertAuthenticationInformation(user: User) {
        if (user.name.isEmpty()) throw Exception("Username has to not be empty for insertion")

        userDao.insertUser(user)
    }

    override fun deleteAuthenticationInformation() {
        userDao.getUser()?.run { userDao.deleteUser(this) }
    }
}