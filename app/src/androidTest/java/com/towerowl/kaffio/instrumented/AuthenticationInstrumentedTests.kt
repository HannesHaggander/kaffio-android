package com.towerowl.kaffio.instrumented

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.towerowl.kaffio.data.LocalDatabase
import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.di.AppComponent
import com.towerowl.kaffio.di.ContextModule
import com.towerowl.kaffio.di.DaggerAppComponent
import com.towerowl.kaffio.di.DatabaseModule
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
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
            .databaseModule(DatabaseModule(localDatabase))
            .build()
    }

    @After
    fun teardown() {
        localDatabase.clearAllTables()
    }

    /**
     * Login happy path login with normal username and verify that information is stored
     */
    @Test
    fun loginWithValidUsernameAndVerifyDataUserIsStoredLocally() {
        val loginUser = User(name = "Test user")

        daggerComponent.run {
            authenticationViewModel().login(loginUser)
            authenticationViewModel().viewModelScope.launch {
                authenticationRepository().getAuthenticationData()?.also { user ->
                    Assert.assertEquals(user, loginUser)
                } ?: Assert.fail("User was null after insertion")
            }
        }
    }

    @Test
    fun loginWhenUserPresent() = runBlocking {
        val userA = User(name = "User A")
        val userB = User(name = "User B")

        daggerComponent.run {
            authenticationViewModel().login(userA)
            authenticationViewModel().login(userB)

            authenticationViewModel().getAuthenticationData { r ->
                if (r.failed()) Assert.fail()

                r.data?.run { Assert.assertEquals(userA, this) }
            }
        }
    }

    /**
     * Verify that logout functionality removes all the data
     */
    @Test
    fun verifyAuthenticationDataRemovedOnLogout() = runBlocking {
        daggerComponent.run {
            authenticationViewModel().logout()
            authenticationViewModel().getAuthenticationData { r -> Assert.assertNull(r.data) }
        }
    }
}