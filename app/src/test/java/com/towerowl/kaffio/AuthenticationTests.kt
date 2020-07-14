package com.towerowl.kaffio

import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.data.daos.UserDao
import com.towerowl.kaffio.repositories.AuthenticationRepository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class AuthenticationTests {

    @Test
    fun `verify error on empty username login`() {
        val emptyUser = User(name = "")
        try {
            AuthenticationRepository(Mockito.mock(UserDao::class.java))
                .insertAuthenticationInformation(emptyUser)
            Assert.fail()
        } catch (e: Exception) {
        }
    }

}