package com.labactivity.synthronize

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.labactivity.synthronize.databinding.ActivityEditProfileBinding
import com.labactivity.synthronize.model.UserModel
import com.labactivity.synthronize.utils.FirebaseUtil

class EditProfile : AppCompatActivity() {
    private lateinit var binding:ActivityEditProfileBinding
    private var fullName:String = ""
    private var userID:String = ""
    private var createdTimestamp = Timestamp.now()
    private var chatroomList: List<String> = listOf()
    //To be implemented
    private var birthday = "To be implemented"
    private var username = "To be implemented"
    private var description = "To be implemented"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveCurrentUserDetails()

        binding.backBtn.setOnClickListener {
            if (isModified()){
                //Dialog to be implemented for saving
                validateUserProfile()
            } else {
                this.finish()
            }
        }

        binding.saveBtn.setOnClickListener {
            //Loading start to be implemented
            if (isModified())
                validateUserProfile()
            else
                this.finish()
        }
    }


    private fun validateUserProfile() {
        if (binding.fullNameEdtTxt.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show()
            //red tint in edtTxt to be implemented
        } else if (binding.usernameEdtTxt.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
            //red tint in edtTxt to be implemented
        } else if (binding.birthdayEdtTxt.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter your birthday", Toast.LENGTH_SHORT).show()
            //red tint in edtTxt to be implemented
        } else {
            //Loading to be implemented
            //Set User Details
            setCurrentUserDetails()
        }
    }

    private fun setCurrentUserDetails() {
        fullName = binding.fullNameEdtTxt.text.toString()
        //other fields to be implemented
        username = binding.usernameEdtTxt.text.toString()
        birthday = binding.birthdayEdtTxt.text.toString()
        description = binding.descriptionEdtTxt.text.toString()

        val userModel = UserModel(
            fullName,
            createdTimestamp,
            userID,
            chatroomList
            //other fields to be implemented
        )

        FirebaseUtil().currentUserDetails().set(userModel).addOnCompleteListener {taskSet ->
            if (taskSet.isSuccessful){
                Toast.makeText(this, "User details successfully updated", Toast.LENGTH_SHORT).show()
                backToProfile()
            } else {
                Toast.makeText(this, "Error in updating user details, please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun retrieveCurrentUserDetails() {
        FirebaseUtil().currentUserDetails().get().addOnSuccessListener {
            if (it.exists()){
                //Retrieve user data from firebase
                fullName = it.get("fullName") as String
                createdTimestamp = it.get("createdTimestamp") as Timestamp
                userID = it.get("userID") as String
                chatroomList = it.get("chatroomList") as List<String>

                //bind user details
                binding.fullNameEdtTxt.setText(fullName)
                binding.usernameEdtTxt.setText(username)
                binding.birthdayEdtTxt.setText(birthday)
                binding.descriptionEdtTxt.setText(description)
            }
        }
    }

    private fun isModified(): Boolean {
        return binding.fullNameEdtTxt.text.toString() != fullName ||
            binding.usernameEdtTxt.text.toString() != username ||
                //To be implemented
            binding.descriptionEdtTxt.text.toString() != description ||
            binding.birthdayEdtTxt.text.toString() != birthday
    }
    private fun backToProfile() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fragment", "profile")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}