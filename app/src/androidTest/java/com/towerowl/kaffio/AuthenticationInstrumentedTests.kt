package com.towerowl.kaffio

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.towerowl.kaffio.data.LocalDatabase
import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.di.*
import com.towerowl.kaffio.repositories.AuthenticationRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthenticationInstrumentedTests {

    lateinit var daggerComponent: AppComponent
    lateinit var context: Context
    lateinit var localDatabase: LocalDatabase

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        daggerComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(context))
            .repositoriesModule(RepositoriesModule(localDatabase))
            .viewModelsModule(ViewModelsModule(AuthenticationRepository(localDatabase.userDao())))
            .build()
    }

    /**
     * Login happy path login with normal username and verify that information is stored
     */
    @Test
    fun loginWithValidUsernameAndVerifyDataUserIsStoredLocally() {
        val loginUser = User(name = "Test user")

        daggerComponent.run {
            authenticationViewModel().login(loginUser)
            authenticationRepository().getAuthenticationData()?.also { user ->
                Assert.assertEquals(user, loginUser)
            } ?: Assert.fail("User was null after insertion")
        }
    }

    @Test
    fun loginWhenUserPresent(){
        val userA = User(name = "User A")
        val userB = User(name = "User B")

        daggerComponent.run {
            authenticationViewModel().login(userA)
            authenticationViewModel().login(userB)
            authenticationRepository().getAuthenticationData()?.also { savedUser ->
                Assert.assertEquals(userA, savedUser)
            } ?: Assert.fail()
        }
    }

    /**
     * Verify that logout functionality removes all the data
     */
    @Test
    fun verifyAuthenticationDataRemovedOnLogout() {
        daggerComponent.run {
            authenticationViewModel().logout()
            Assert.assertNull(authenticationRepository().getAuthenticationData())
        }
    }
}