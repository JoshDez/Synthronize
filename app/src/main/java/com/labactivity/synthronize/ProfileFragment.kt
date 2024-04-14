package com.labactivity.synthronize

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.labactivity.synthronize.databinding.ActivityMainBinding
import com.labactivity.synthronize.databinding.FragmentExploreBinding
import com.labactivity.synthronize.databinding.FragmentNotificationBinding
import com.labactivity.synthronize.databinding.FragmentProfileBinding
import com.labactivity.synthronize.model.UserModel
import com.labactivity.synthronize.utils.FirebaseUtil

class ProfileFragment(private var mainBinding: ActivityMainBinding) : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding.toolbarTitleTV.text = "PROFILE"

        FirebaseUtil().currentUserDetails().get().addOnSuccessListener {
            if (it.exists()){
                userModel = UserModel(
                    fullName = it.get("fullName") as String,
                    userID = it.get("userID") as String,
                    createdTimestamp = it.get("createdTimestamp") as Timestamp,
                    chatroomList = it.get("chatroomList") as List<String>
                )
                bindUserDetails()
            }
        }

    }

    private fun bindUserDetails() {
        binding.userNameTV.text = userModel.userID
        binding.userDisplayNameTV.text = userModel.fullName
    }
}