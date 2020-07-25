package com.towerowl.kaffio

import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.data.daos.UserDao
import com.towerowl.kaffio.repositories.AuthenticationRepository
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class AuthenticationTests {

    @ExperimentalCoroutinesApi
    @Test(expected = Exception::class)
    fun `verify error on empty username login`() = runBlockingTest {
        val emptyUser = User(name = "")

        AuthenticationRepository(mockkClass(UserDao::class))
            .insertAuthenticationInformation(emptyUser)
    }

}