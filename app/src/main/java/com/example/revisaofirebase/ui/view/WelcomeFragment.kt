package com.example.revisaofirebase.ui.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.revisaofirebase.R
import com.example.revisaofirebase.ui.viewModel.WelcomeViewModel

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private val viewModel: WelcomeViewModel by viewModels()

    private lateinit var tvWelcome: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScreen(view)
    }

    private fun setupScreen(view: View) {
        tvWelcome = view.findViewById(R.id.tv_welcome)
        val loggedUserEmail = viewModel.loggedUser.email
        tvWelcome.text = getString(R.string.welcome_text, loggedUserEmail)
    }

}