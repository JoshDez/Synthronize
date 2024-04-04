package com.labactivity.synthronize.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.labactivity.synthronize.databinding.ItemMessageBinding
import com.labactivity.synthronize.model.MessageModel
import com.labactivity.synthronize.utils.FirebaseUtil

class MessageAdapter(private val context: Context, options: FirestoreRecyclerOptions<MessageModel>):
    FirestoreRecyclerAdapter<MessageModel, MessageAdapter.MessageViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: MessageModel) {
        holder.bind(model)
    }

    class MessageViewHolder(private val binding: ItemMessageBinding, private val context: Context): RecyclerView.ViewHolder(binding.root){

        fun bind(model: MessageModel){
            if (model.senderID == FirebaseUtil().currentUserUid()){
                //If user is sender
                binding.recieverLayout.visibility = View.GONE
                binding.senderMsgTV.text = model.message
            } else {
                var name = ""
                val ref = FirebaseUtil().targetUserDetails(model.senderID).get().addOnSuccessListener {
                    if (it.exists()){
                        name = it.getString("fullName").toString()
                        //other details like profile picture
                    }
                    //if user is reciever
                    binding.senderLayout.visibility = View.GONE
                    binding.recieverMsgTV.text = model.message
                    binding.userNameTV.text = name
                }
            }
        }




    }

}