package com.example.revisaofirebase.data

import com.example.revisaofirebase.data.dto.UserDTO

class UserRepository {

    private val userAuth = UserAuth
    val loggedUser: UserDTO?
        get() = userAuth.loggedUser

    fun registerUser(user: UserDTO) = userAuth.registerUser(user)
    fun loginUser(user: UserDTO) = userAuth.loginUser(user)

}