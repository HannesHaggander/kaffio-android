package com.towerowl.kaffio.models

import androidx.lifecycle.ViewModel
import com.towerowl.kaffio.data.User
import com.towerowl.kaffio.repositories.IAuthenticationRepository

class AuthenticationViewModel(
    private val authenticationRepository: IAuthenticationRepository
) : ViewModel() {

    fun login(user: User) {
        authenticationRepository.getAuthenticationData()?.run { return }

        authenticationRepository.insertAuthenticationInformation(user)
    }

    fun logout() {
        authenticationRepository.deleteAuthenticationInformation()
    }

}