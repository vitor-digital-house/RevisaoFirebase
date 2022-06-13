package com.example.revisaofirebase.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisaofirebase.data.Result
import com.example.revisaofirebase.data.UserRepository
import com.example.revisaofirebase.util.StringUtils
import kotlinx.coroutines.launch

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

            userRepository.registerUser(email, password).addOnCompleteListener { task ->
                _registerLiveData.value = if (task.isSuccessful) {
                    Result.Success(data = Unit)
                } else {
                    Result.Error
                }
            }
        }
    }

    fun isValidEmail(givenEmail: String): Boolean = StringUtils.isValidEmail(givenEmail)

    fun isValidPassword(givenPassword: String): Boolean = StringUtils.isValidPassword(givenPassword)
}