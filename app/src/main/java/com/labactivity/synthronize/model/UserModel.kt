package com.labactivity.synthronize.model

import com.google.firebase.Timestamp

data class UserModel(
    val fullName: String = "",
    val createdTimestamp: Timestamp
)