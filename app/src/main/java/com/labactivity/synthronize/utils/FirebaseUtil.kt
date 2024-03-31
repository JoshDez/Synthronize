package com.labactivity.synthronize.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUtil {

    private fun currentUserUid(): String {
        return FirebaseAuth.getInstance().uid!!

    }

    fun isLoggedIn(): Boolean {
        return currentUserUid() != null
    }

    fun currentUserDetails(): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserUid())
    }
}