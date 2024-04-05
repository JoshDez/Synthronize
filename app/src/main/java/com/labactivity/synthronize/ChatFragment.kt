package com.labactivity.synthronize

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.labactivity.synthronize.adapters.ChatroomAdapter
import com.labactivity.synthronize.databinding.ActivityMainBinding
import com.labactivity.synthronize.databinding.FragmentChatBinding
import com.labactivity.synthronize.model.ChatroomModel
import com.labactivity.synthronize.utils.FirebaseUtil

class ChatFragment(private val mainBinding: ActivityMainBinding) : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatroomAdapter: ChatroomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding.toolbarTitleTV.text = "INBOX"
        recyclerView = binding.chatroomsRV
        recyclerView.layoutManager = LinearLayoutManager(activity)
        getChatroomList()
    }

    private fun getChatroomList(){
        //Retrieves current user chatroomList
        FirebaseUtil().currentUserDetails().get().addOnSuccessListener {
            if (it.exists()){
                val chatroomList = it.get("chatroomList") as? ArrayList<String>
                if (!chatroomList.isNullOrEmpty()) {
                    val myQuery:Query = FirebaseUtil().retrieveAllChatRoomReferences().whereIn(FieldPath.documentId(), chatroomList)
                        .orderBy("lastMsgTimestamp", Query.Direction.DESCENDING)
                    setupChatroomListRV(myQuery)
                }
            }
        }
    }

    private fun setupChatroomListRV(query: Query){
        val options: FirestoreRecyclerOptions<ChatroomModel> =
            FirestoreRecyclerOptions.Builder<ChatroomModel>().setQuery(query, ChatroomModel::class.java).build()
        chatroomAdapter = ChatroomAdapter(requireContext(), options)
        recyclerView.adapter = chatroomAdapter
        chatroomAdapter.startListening()
    }
}