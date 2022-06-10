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
import com.example.revisaofirebase.ui.viewModel.RegisterViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var clContent: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var tilEmail: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var btnGoToLogin: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setupListeners()
        setupObserver()
    }

    private fun initViews(view: View) {
        clContent = view.findViewById(R.id.register_content)
        progressBar = view.findViewById(R.id.register_progress)
        tilEmail = view.findViewById(R.id.til_register_email)
        etEmail = view.findViewById(R.id.et_email)
        tilPassword = view.findViewById(R.id.til_register_password)
        etPassword = view.findViewById(R.id.et_password)
        btnRegister = view.findViewById(R.id.btn_register)
        btnGoToLogin = view.findViewById(R.id.btn_go_to_login)
    }

    private fun setupListeners() {
        btnRegister.setOnClickListener {
            clearErrors()
            val email = etEmail.text.toString()
            val pwd = etPassword.text.toString()
            if (viewModel.isValidEmail(etEmail.text.toString()).not()) {
                showInvalidEmailError()
                return@setOnClickListener
            } else if (viewModel.isValidPassword(etPassword.text.toString()).not()) {
                showInvalidPasswordError()
                return@setOnClickListener
            }
            viewModel.doRegister(email, pwd)
        }

        btnGoToLogin.setOnClickListener { findNavController().popBackStack() }
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
        viewModel.registerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading()
                is Result.Error -> showError()
                is Result.Success -> showSuccess()
            }
        }
    }

    private fun showLoading() {
        progressBar.isVisible = true
        clContent.isVisible = false
    }

    private fun showError() {
        showContent()
        Toast.makeText(
            requireContext(),
            GENERIC_ERROR_MSG,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showContent() {
        progressBar.isVisible = false
        clContent.isVisible = true
    }

    private fun showSuccess() {
        showContent()
        btnRegister.isEnabled = false
        tilEmail.isEnabled = false
        tilPassword.isEnabled = false
        btnGoToLogin.isVisible = true
        Toast.makeText(
            requireContext(),
            SUCCESS_MSG,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val SUCCESS_MSG = "New user registered with success!"
        private const val GENERIC_ERROR_MSG = "Ops, something went wrong. Please try again."
        private const val EMAIL_ERROR_MSG = "Please provide a valid e-mail."
        private const val PASSWORD_ERROR_MSG = "Password must have at least 8 characters."
    }
}