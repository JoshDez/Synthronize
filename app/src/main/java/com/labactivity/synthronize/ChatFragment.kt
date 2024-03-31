package com.labactivity.synthronize

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.labactivity.synthronize.databinding.ActivityMainBinding
import com.labactivity.synthronize.databinding.FragmentChatBinding
import com.labactivity.synthronize.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
class ChatFragment : Fragment {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentChatBinding
    private lateinit var mainBinding: ActivityMainBinding

    constructor() : super()
    constructor(mainBinding: ActivityMainBinding): super() {
        this.mainBinding = mainBinding
    }

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
    }
}