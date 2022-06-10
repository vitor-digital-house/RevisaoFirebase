package com.example.revisaofirebase.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.revisaofirebase.R
import com.example.revisaofirebase.data.Result
import com.example.revisaofirebase.ui.viewModel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var clContent: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var etEmail: TextInputEditText
    private lateinit var tilEmail: TextInputLayout
    private lateinit var etPassword: TextInputEditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setupListeners()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        clearErrors()
    }

    private fun initViews(view: View) {
        clContent = view.findViewById(R.id.login_content)
        progressBar = view.findViewById(R.id.login_progress)
        etEmail = view.findViewById(R.id.et_email)
        tilEmail = view.findViewById(R.id.til_login_email)
        etPassword = view.findViewById(R.id.et_password)
        tilPassword = view.findViewById(R.id.til_login_password)
        btnLogin = view.findViewById(R.id.btn_login)
        btnRegister = view.findViewById(R.id.btn_register)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            clearErrors()
            val email = etEmail.text.toString()
            val pwd = etPassword.text.toString()
            if (viewModel.isValidEmail(email).not()) {
                showInvalidEmailError()
                return@setOnClickListener
            } else if (viewModel.isValidPassword(pwd).not()) {
                showInvalidPasswordError()
                return@setOnClickListener
            }
            viewModel.doLogin(email, pwd)
        }

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun clearErrors() {
        tilEmail.error = null
        tilPassword.error = null
    }

    private fun showInvalidEmailError() {
        tilEmail.error = EMAIL_ERROR_MSG
    }

    private fun showInvalidPasswordError() {
        tilPassword.error = PASSWORD_ERROR_MSG
    }

    private fun setupObserver() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading()
                is Result.Error -> showLoginError()
                is Result.Success -> accessWelcomeScreen()
            }
        }
    }

    private fun showLoading() {
        progressBar.isVisible = true
        clContent.isVisible = false
    }

    private fun showLoginError() {
        progressBar.isVisible = false
        clContent.isVisible = true
        Toast.makeText(
            requireContext(),
            GENERIC_ERROR_MSG,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun accessWelcomeScreen() =
        findNavController().navigate(R.id.action_loginFragment_to_WelcomeFragment)

    companion object {
        private const val EMAIL_ERROR_MSG = "Please provide a valid e-mail."
        private const val PASSWORD_ERROR_MSG = "Password must have at least 8 characters."
        private const val GENERIC_ERROR_MSG = "Ops, something went wrong. Please try again."
    }
}