package com.labactivity.synthronize

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Timestamp
import com.labactivity.synthronize.databinding.ActivityEditProfileBinding
import com.labactivity.synthronize.model.UserModel
import com.labactivity.synthronize.utils.FirebaseUtil
import com.labactivity.synthronize.utils.ModelHandler

class EditProfile : AppCompatActivity() {
    private lateinit var binding:ActivityEditProfileBinding
    private lateinit var userModel: UserModel
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickedImageUri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            //Image is selected
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
                if (data != null && data.data != null){
                    pickedImageUri = data.data!!
                    setUserProfilePic(pickedImageUri)
                }
            }
        }

        retrieveAndBindCurrentUserDetails()
        bindSetOnClickListeners()
    }

    private fun bindSetOnClickListeners(){
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

        binding.userProfileCIV.setOnClickListener {
            ImagePicker.with(this).cropSquare().compress(512)
                .maxResultSize(512, 512)
                .createIntent {
                    imagePickerLauncher.launch(it)
                }
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
        //set new user information
        FirebaseUtil().currentUserDetails().set(userModel).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "User details successfully updated", Toast.LENGTH_SHORT).show()
                backToProfile()
            } else {
                Toast.makeText(this, "Error in updating user details, please try again", Toast.LENGTH_SHORT).show()
            }
        }

        if (pickedImageUri != null){
            //FirebaseUtil().getUserProfilePicRef(FirebaseUtil().currentUserUid()).putFile(pickedImageUri)
        }
    }

    private fun retrieveAndBindCurrentUserDetails() {
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
            binding.descriptionEdtTxt.text.toString() != userModel.description ||
                //TODO: birthday is still String
            binding.birthdayEdtTxt.text.toString() != userModel.birthday
    }
    private fun backToProfile() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fragment", "profile")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun setUserProfilePic(imageUri: Uri){
        Glide.with(this).load(imageUri)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.userProfileCIV)
    }
}