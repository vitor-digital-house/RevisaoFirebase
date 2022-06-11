package com.example.revisaofirebase.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    val loggedUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    suspend fun registerUser(email: String, password: String) = withContext(Dispatchers.IO) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    suspend fun loginUser(email: String, password: String) = withContext(Dispatchers.IO) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
    }

}