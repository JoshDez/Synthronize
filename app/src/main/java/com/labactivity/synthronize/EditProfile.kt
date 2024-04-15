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
import com.labactivity.synthronize.utils.ModelHandler

class EditProfile : AppCompatActivity() {
    private lateinit var binding:ActivityEditProfileBinding
    private lateinit var userModel: UserModel

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
            userModel.fullName = binding.fullNameEdtTxt.text.toString()
            userModel.username = binding.usernameEdtTxt.text.toString()
            userModel.description = binding.descriptionEdtTxt.text.toString()
            //other fields to be implemented
            userModel.birthday = binding.birthdayEdtTxt.text.toString()
            setCurrentUserDetails()
        }
    }

    private fun setCurrentUserDetails() {
        FirebaseUtil().currentUserDetails().set(userModel).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "User details successfully updated", Toast.LENGTH_SHORT).show()
                backToProfile()
            } else {
                Toast.makeText(this, "Error in updating user details, please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun retrieveCurrentUserDetails() {
        ModelHandler().retrieveUserModel(FirebaseUtil().currentUserUid()) {result ->
            //bind user details
            userModel = result
            binding.fullNameEdtTxt.setText(userModel.fullName)
            binding.usernameEdtTxt.setText(userModel.username)
            binding.birthdayEdtTxt.setText(userModel.birthday)
            binding.descriptionEdtTxt.setText(userModel.description)
        }
    }

    private fun isModified(): Boolean {
        return binding.fullNameEdtTxt.text.toString() != userModel.fullName ||
            binding.usernameEdtTxt.text.toString() != userModel.username ||
                //To be implemented
            binding.descriptionEdtTxt.text.toString() != userModel.description ||
            binding.birthdayEdtTxt.text.toString() != userModel.birthday
    }
    private fun backToProfile() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fragment", "profile")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}