package com.labactivity.synthronize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.labactivity.synthronize.adapters.MessageAdapter
import com.labactivity.synthronize.databinding.ActivityChatroomBinding
import com.labactivity.synthronize.model.ChatroomModel
import com.labactivity.synthronize.model.MessageModel
import com.labactivity.synthronize.utils.FirebaseUtil

class Chatroom : AppCompatActivity() {
    private lateinit var binding:ActivityChatroomBinding
    private lateinit var chatroomID:String
    private lateinit var chatroomModel: ChatroomModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var chatroomName = ""
    private var receiverUid = ""
    private var groupId = ""
    private var isDM = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatroomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatroomName = intent.getStringExtra("chatroomName").toString()
        //groupId = intent.getStringExtra("groupID").toString()
        isDM = intent.getBooleanExtra("isDM", false)
        receiverUid = intent.getStringExtra("userID").toString()


        binding.chatRoomNameTV.text = chatroomName
        binding.backBtn.setOnClickListener {
            this.finish()
        }

        binding.sendMsgBtn.setOnClickListener {
            val message = binding.chatBoxEdtTxt.text.toString()
            if (message.isNotEmpty()){
                sendMessage(message)
                binding.chatBoxEdtTxt.setText("")
            }
        }


        chatroomID = FirebaseUtil().getChatRoomID(FirebaseUtil().currentUserUid(), receiverUid)
        createOrRetrieveChatroomModel()
        setupChatRV()
    }

    private fun setupChatRV() {
        val myQuery: Query = FirebaseUtil().retrieveChatsFromChatroom(chatroomID)
            .orderBy("timestamp", Query.Direction.DESCENDING)

        val options: FirestoreRecyclerOptions<MessageModel> =
            FirestoreRecyclerOptions.Builder<MessageModel>().setQuery(myQuery, MessageModel::class.java).build()

        recyclerView = binding.chatRV
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        recyclerView.layoutManager = linearLayoutManager
        messageAdapter = MessageAdapter(this, options)
        recyclerView.adapter = messageAdapter
        messageAdapter.startListening()
        messageAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                recyclerView.smoothScrollToPosition(0)
            }
        })
    }

    private fun sendMessage(message:String) {
        val messageModel = MessageModel(message, FirebaseUtil().currentUserUid(), Timestamp.now())
        chatroomModel.lastMessageUserId = FirebaseUtil().currentUserUid()
        chatroomModel.lastMessage = message
        chatroomModel.lastMsgTimestamp = Timestamp.now()

        //update lastMessageUserID and lastMsgTimestamp
        FirebaseUtil().retrieveChatRoomReference(chatroomID).set(chatroomModel)

        //add message to chatroom
        FirebaseUtil().retrieveChatsFromChatroom(chatroomID).add(messageModel)
    }

    private fun createOrRetrieveChatroomModel(){
        FirebaseUtil().retrieveChatRoomReference(chatroomID).get().addOnCompleteListener {
            if (it.isSuccessful){
                if (!it.result.exists() && isDM){
                    //First chat in DM
                    chatroomModel = ChatroomModel(chatroomID,
                        "direct_message",
                        listOf(FirebaseUtil().currentUserUid(), receiverUid),
                        Timestamp.now(),
                        ""
                    )
                    FirebaseUtil().retrieveChatRoomReference(chatroomID).set(chatroomModel)
                } else if (!it.result.exists() && !isDM){
                    //First chat in Group
                    //chatroomModel = ChatroomModel(chatroomID, "group_chat")
                } else {
                    chatroomModel = it.result.toObject(ChatroomModel::class.java)!!
                }

            }else {
                //leave it blank for now
            }
        }
    }
}