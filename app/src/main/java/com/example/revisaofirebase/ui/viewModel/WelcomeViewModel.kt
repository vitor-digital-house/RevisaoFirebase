package com.example.revisaofirebase.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.revisaofirebase.data.dto.UserDTO
import com.example.revisaofirebase.data.UserRepository
import com.example.revisaofirebase.ui.vo.UserVO

class WelcomeViewModel : ViewModel() {

    private val userRepository = UserRepository()
    val loggedUser: UserVO
        get() = getLoggedUserFromRepository()

    private fun getLoggedUserFromRepository() = convertUserDTOToVO(userRepository.loggedUser!!)

    private fun convertUserDTOToVO(userDTO: UserDTO) = UserVO(email = userDTO.email)

}