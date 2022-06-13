package com.example.revisaofirebase.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisaofirebase.data.Result
import com.example.revisaofirebase.data.UserRepository
import com.example.revisaofirebase.util.StringUtils
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

            userRepository.loginUser(email, password).addOnCompleteListener { task ->
                _loginLiveData.value = if (task.isSuccessful) {
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