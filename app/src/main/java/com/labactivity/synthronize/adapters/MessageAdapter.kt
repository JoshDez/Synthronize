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
            var name = ""

            //get user details
            FirebaseUtil().targetUserDetails(model.senderID).get().addOnSuccessListener {
                if (it.exists()){
                    name = it.getString("fullName").toString()
                    binding.userNameTV.text = name
                }
            }

            //bind user's message
            if (model.senderID == FirebaseUtil().currentUserUid()){
                //If user is sender
                binding.recieverLayout.visibility = View.GONE
                binding.senderLayout.visibility = View.VISIBLE
                binding.senderMsgTV.text = model.message
            } else {
                binding.senderLayout.visibility = View.GONE
                binding.recieverLayout.visibility = View.VISIBLE
                binding.recieverMsgTV.text = model.message
            }
        }




    }

}