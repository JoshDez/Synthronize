package com.labactivity.synthronize.model

import com.google.firebase.Timestamp

data class UserModel(
    var fullName: String = "",
    var createdTimestamp: Timestamp = Timestamp.now(),
    var userID: String = "",
    var chatroomList:List<String> = listOf(),
    var description:String = "",
    var username:String = "",
    var birthday:String = "",
)