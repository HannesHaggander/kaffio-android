package com.towerowl.kaffio.models

import com.towerowl.kaffio.enums.Status

data class CoResult<T>(
    val data: T,
    val status: Status
) {
    fun succeeded(): Boolean = status == Status.Success

    fun failed(): Boolean = status == Status.Error
}