package com.example.revisaofirebase.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisaofirebase.data.Result
import com.example.revisaofirebase.data.dto.UserDTO
import com.example.revisaofirebase.data.UserRepository
import com.example.revisaofirebase.util.StringUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _loginLiveData = MutableLiveData<Result<Unit>>()
    val loginLiveData: LiveData<Result<Unit>> = _loginLiveData

    fun doLogin(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _loginLiveData.value = Result.Loading

            delay(1_200)

            val user = UserDTO(email, password)
            userRepository.loginUser(user)

            _loginLiveData.value =
                if (userRepository.loggedUser != null) Result.Success(Unit) else Result.Error
        }
    }

    fun isValidEmail(givenEmail: String): Boolean = StringUtils.isValidEmail(givenEmail)

    fun isValidPassword(givenPassword: String): Boolean = StringUtils.isValidPassword(givenPassword)
}