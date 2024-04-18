package com.example.synthronize

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.synthronize.databinding.ActivityOtherUserProfileBinding
import com.example.synthronize.model.UserModel
import com.example.synthronize.utils.AppUtil
import com.example.synthronize.utils.FirebaseUtil

class OtherUserProfile : AppCompatActivity() {
    private lateinit var binding:ActivityOtherUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userID = intent.getStringExtra("userID").toString()
        bindUserDetails(userID)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindUserDetails(userID:String) {
        //TODO: Implement loading start

        FirebaseUtil().targetUserDetails(userID).get().addOnCompleteListener {
            if (it.isSuccessful && it.result.exists()){

                val userModel = it.result.toObject(UserModel::class.java)!!

                binding.userDescriptionTV.text = userModel.description
                binding.userNameTV.text = userModel.username
                binding.userDisplayNameTV.text = userModel.fullName
                //TODO: add to birthday binding and created timestamp

                AppUtil().setUserProfilePic(this, userID, binding.userProfileCIV)
                AppUtil().setUserCoverPic(this, userID, binding.userCoverIV)

                binding.messageUserBtn.setOnClickListener {
                    val intent = Intent(this, Chatroom::class.java)
                    intent.putExtra("chatroomName", userModel.fullName)
                    intent.putExtra("userID", userID)
                    intent.putExtra("chatroomType", "direct_message")
                    startActivity(intent)
                }
                //TODO: Implement loading stop
            }
        }
    }

}