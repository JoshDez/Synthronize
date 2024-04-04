package com.labactivity.synthronize

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.labactivity.synthronize.adapters.ChatroomAdapter
import com.labactivity.synthronize.adapters.SearchUserAdapter
import com.labactivity.synthronize.databinding.ActivityMainBinding
import com.labactivity.synthronize.databinding.FragmentChatBinding
import com.labactivity.synthronize.databinding.FragmentProfileBinding
import com.labactivity.synthronize.model.ChatroomModel
import com.labactivity.synthronize.utils.FirebaseUtil

// TODO: Rename parameter arguments, choose names that match
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

        //Retrieves current user chatroom collection
        val userChatroomsCollection = FirebaseUtil().retrieveUsersChatRooms(FirebaseUtil().currentUserUid())
        val chatroomsList:ArrayList<String> = ArrayList()

        userChatroomsCollection.get().addOnSuccessListener {
            for (document in it){
                chatroomsList.add(document.id)
            }
            if (chatroomsList.size > 0){
                val myQuery:Query = FirebaseUtil().retrieveAllChatRoomReferences().whereIn(FieldPath.documentId(), chatroomsList)
                
                setupChatroomsRV(myQuery)
            }
        }
    }

    fun setupChatroomsRV(query: Query){

        val options: FirestoreRecyclerOptions<ChatroomModel> =
            FirestoreRecyclerOptions.Builder<ChatroomModel>().setQuery(query, ChatroomModel::class.java).build()
        chatroomAdapter = ChatroomAdapter(requireContext(), options)
        recyclerView.adapter = chatroomAdapter
        chatroomAdapter.startListening()


    }
}