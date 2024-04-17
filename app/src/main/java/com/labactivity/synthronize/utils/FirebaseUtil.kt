package com.labactivity.synthronize.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference

class FirebaseUtil {

    //For Authentication
    fun currentUserUid(): String {
        return FirebaseAuth.getInstance().uid!!
    }
    fun isLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().uid != null
    }

    //For retrieving users documents
    fun currentUserDetails(): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserUid())
    }
    fun targetUserDetails(targetUid:String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(targetUid)
    }
    fun allUsersCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")
    }


    //For Chat Function
    fun retrieveChatRoomReference(chatroomID:String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("chatroom").document(chatroomID)
    }

    fun retrieveAllChatRoomReferences(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("chatroom")
    }

    fun retrieveChatsFromChatroom(chatroomID: String): CollectionReference {
        return retrieveChatRoomReference(chatroomID).collection("chats")
    }

    fun getChatRoomID(uid1:String, uid2:String): String{
        return if (uid1.hashCode() < uid2.hashCode()){
            "$uid1-$uid2"
        } else {
            "$uid2-$uid1"
        }
    }

    //For Firebase Storage
/*
    fun getUserProfilePicRef(uid:String): StorageReference {
        return FirebaseStorage.getInstance().reference
            .child("userProfilePicture")
            //name of picture
            .child(uid)
    }
*/
}