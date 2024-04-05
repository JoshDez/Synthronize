package com.labactivity.synthronize.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.labactivity.synthronize.Chatroom
import com.labactivity.synthronize.databinding.ItemChatroomBinding
import com.labactivity.synthronize.model.ChatroomModel
import com.labactivity.synthronize.model.UserModel
import com.labactivity.synthronize.utils.FirebaseUtil
import java.util.logging.Handler

class ChatroomAdapter(private val context: Context, options: FirestoreRecyclerOptions<ChatroomModel>):
    FirestoreRecyclerAdapter<ChatroomModel, ChatroomAdapter.ChatroomViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatroomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatroomBinding.inflate(inflater, parent, false)
        return ChatroomViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ChatroomViewHolder, position: Int, model: ChatroomModel) {
        holder.bind(model)
    }


    class ChatroomViewHolder(private val binding: ItemChatroomBinding, private val context: Context): RecyclerView.ViewHolder(binding.root){

        private lateinit var chatroomModel: ChatroomModel

        fun bind(model: ChatroomModel){
            chatroomModel = model

            if (chatroomModel.chatroomType == "direct_message"){
                //if the user is id
                if (chatroomModel.userIdList[0] != FirebaseUtil().currentUserUid()){
                    bindUser(chatroomModel.userIdList[0])
                } else {
                    bindUser(chatroomModel.userIdList[1])
                }
            } else {
                //TO BE IMPLEMENTED FOR GROUP CHATS
                bindGroupChat()
            }
        }

        private fun bindGroupChat() {
            //TO BE IMPLEMENTED
        }

        //Gawan ng firebase util
        private fun bindUser(uid:String){
            FirebaseUtil().targetUserDetails(uid).get().addOnSuccessListener {
                val fullName = it.getString("fullName").toString()
                binding.chatroomNameTV.text = fullName
                //other fields

                binding.chatroomLayout.setOnClickListener {
                    val intent = Intent(context, Chatroom::class.java)
                    intent.putExtra("chatroomName", fullName)
                    intent.putExtra("userID", uid)
                    intent.putExtra("isDM", true)
                    context.startActivity(intent)
                }
            }
        }




    }

}