package com.towerowl.kaffio.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.enums.Status
import com.towerowl.kaffio.repositories.AuthenticationRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun login(user: User) {
        viewModelScope.launch(IO) {
            authenticationRepository.getAuthenticationData()?.run { return@launch }

            authenticationRepository.insertAuthenticationInformation(user)
        }
    }

    fun logout() {
        viewModelScope.launch(IO) {
            authenticationRepository.deleteAuthenticationInformation()
        }
    }

    fun getAuthenticationData(result: (CoResult<User?>) -> Unit) = viewModelScope.launch {
        authenticationRepository.getAuthenticationData().run {
            result.invoke(CoResult(this, if (this != null) Status.Success else Status.Error))
        }
    }

}