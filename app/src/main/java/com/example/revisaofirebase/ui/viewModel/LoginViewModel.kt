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

            val task = userRepository.loginUser(email, password)

            task.addOnFailureListener {
                _loginLiveData.value = Result.Error
            }

            task.addOnSuccessListener {
                _loginLiveData.value = Result.Success(data = Unit)
            }
        }
    }

    fun isValidEmail(givenEmail: String): Boolean = StringUtils.isValidEmail(givenEmail)

    fun isValidPassword(givenPassword: String): Boolean = StringUtils.isValidPassword(givenPassword)
}