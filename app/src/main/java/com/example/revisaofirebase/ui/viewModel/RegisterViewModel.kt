package com.example.revisaofirebase.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisaofirebase.data.Result
import com.example.revisaofirebase.data.UserRepository
import com.example.revisaofirebase.data.dto.UserDTO
import com.example.revisaofirebase.util.StringUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class RegisterViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _registerLiveData = MutableLiveData<Result<Unit>>()
    val registerLiveData: LiveData<Result<Unit>> = _registerLiveData

    fun doRegister(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _registerLiveData.value = Result.Loading

            delay(1_200)

            val user = UserDTO(email, password)
            userRepository.registerUser(user)

            _registerLiveData.value =
                if (Random.nextBoolean()) Result.Success(data = Unit) else Result.Error
        }
    }

    fun isValidEmail(givenEmail: String): Boolean = StringUtils.isValidEmail(givenEmail)

    fun isValidPassword(givenPassword: String): Boolean = StringUtils.isValidPassword(givenPassword)
}