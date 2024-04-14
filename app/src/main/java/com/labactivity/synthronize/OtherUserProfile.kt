package com.labactivity.synthronize

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.labactivity.synthronize.databinding.ActivityOtherUserProfileBinding

class OtherUserProfile : AppCompatActivity() {
    private lateinit var binding:ActivityOtherUserProfileBinding
    private var userID = ""
    private var fullName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userID = intent.getStringExtra("userID").toString()
        fullName = intent.getStringExtra("fullName").toString()
        bindUserDetails()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }




    }

    private fun bindUserDetails() {
        binding.userDescriptionTV.text = ""
        binding.userNameTV.text = userID
        binding.userDisplayNameTV.text = fullName

        binding.messageUserBtn.setOnClickListener {
            val intent = Intent(this, Chatroom::class.java)
            intent.putExtra("chatroomName", fullName)
            intent.putExtra("userID", userID)
            intent.putExtra("isDM", true)
            startActivity(intent)
        }
    }

}