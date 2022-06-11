package com.example.revisaofirebase.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.revisaofirebase.data.UserRepository
import com.example.revisaofirebase.ui.vo.UserVO
import com.google.firebase.auth.FirebaseUser

class WelcomeViewModel : ViewModel() {

    private val userRepository = UserRepository()
    val loggedUser: UserVO
        get() = getLoggedUserFromRepository()

    private fun getLoggedUserFromRepository() = convertFirebaseUserToVO(userRepository.loggedUser!!)

    private fun convertFirebaseUserToVO(firebaseUser: FirebaseUser) =
        UserVO(email = firebaseUser.email!!)

}