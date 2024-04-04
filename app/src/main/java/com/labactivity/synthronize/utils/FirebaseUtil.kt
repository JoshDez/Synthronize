package com.labactivity.synthronize.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUtil {

    fun currentUserUid(): String {
        return FirebaseAuth.getInstance().uid!!
    }

    fun isLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().uid != null
    }

    fun currentUserDetails(): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserUid())
    }

    //Retrieves target user's document
    fun targetUserDetails(targetUid:String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(targetUid)
    }

    fun allUsersCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")
    }

    //Chat
    fun retrieveChatRoomReference(chatroomID:String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("chatroom").document(chatroomID)
    }

    fun retrieveAllChatRoomReferences(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("chatroom")
    }

    fun retrieveChatsFromChatroom(chatroomID: String): CollectionReference {
        return retrieveChatRoomReference(chatroomID).collection("chats")
    }

    fun retrieveUsersChatRooms(targetUid: String): CollectionReference {
        return targetUserDetails(targetUid).collection("userChatrooms")
    }

    fun retrieveMembersFromGroup(groupUid: String): CollectionReference {
        return FirebaseFirestore.getInstance().collection("group").document(groupUid)
            .collection("members")
    }

    fun getChatRoomID(uid1:String, uid2:String): String{
        return if (uid1.hashCode() < uid2.hashCode()){
            "$uid1-$uid2"
        } else {
            "$uid2-$uid1"
        }
    }
    fun getChatRoomID(groupUid:String):String{
        return "$groupUid"
    }
}