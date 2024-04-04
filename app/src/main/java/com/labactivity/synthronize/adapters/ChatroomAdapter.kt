package com.labactivity.synthronize.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
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

        private lateinit var userModel: UserModel

        fun bind(model: ChatroomModel){
            if (model.chatroomType == "direct_message"){
                //if the user is id
                if (model.userIdList[0] != FirebaseUtil().currentUserUid()){
                    getUserModel(model.userIdList[0])
                } else {
                    getUserModel(model.userIdList[1])
                }
            } else {

            }
        }
        //Gawan ng firebase util
        fun getUserModel(uid:String) {
            val ref = FirebaseUtil().targetUserDetails(uid)
                .get().addOnSuccessListener {
                    if (it.exists()){
                        userModel = UserModel(
                            fullName = it.getString("fullName").toString(),
                            //profile pic
                        )

                        binding.userFullNameTV.text = userModel.fullName
                    }
                }
        }




    }

}