package com.example.revisaofirebase.data

import com.example.revisaofirebase.data.dto.UserDTO

object UserAuth {

    private val users: MutableList<UserDTO> = mutableListOf()
    var loggedUser: UserDTO? = null

    fun loginUser(user: UserDTO) {
        if (users.contains(user)) {
            loggedUser = user
        }
    }

    fun registerUser(user: UserDTO) {
        users.add(user)
    }

}