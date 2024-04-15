package com.labactivity.synthronize.utils

import com.google.firebase.Timestamp
import com.labactivity.synthronize.model.UserModel

class ModelHandler {

    fun retrieveUserModel(userID:String, callback: (UserModel) -> Unit){
        FirebaseUtil().targetUserDetails(userID).get().addOnSuccessListener {
            //initializes user model
            var userModel = UserModel()

            if (it.exists()){
                var fullName = it.getString("fullName")
                var userID = it.getString("userID")
                var createdTimestamp = it.getTimestamp("createdTimestamp")
                var chatroomList = it.get("chatroomList") as? List<String>
                var description = it.getString("description")
                var username = it.getString("username")
                var birthday = it.getString("birthday")

                //Replaces null values with placeholders
                if (fullName == null){
                    fullName = ""
                }
                if (userID == null){
                    userID = ""
                }
                if (createdTimestamp == null){
                    createdTimestamp = Timestamp.now()
                }
                if (chatroomList == null){
                    chatroomList = listOf()
                }
                if (description == null){
                    description = ""
                }
                if (username == null){
                    username = ""
                }
                if (birthday == null){
                    birthday = ""
                }

                userModel = UserModel(
                    fullName,
                    createdTimestamp,
                    userID,
                    chatroomList,
                    description,
                    username,
                    birthday
                )

                callback(userModel)

            } else {
                callback(userModel)
            }


        }
    }


}