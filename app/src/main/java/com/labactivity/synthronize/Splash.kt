package com.labactivity.synthronize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.labactivity.synthronize.databinding.ActivitySplashBinding
import com.labactivity.synthronize.utils.FirebaseUtil

class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val intent = if(FirebaseUtil().isLoggedIn()){
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, Login::class.java)
            }
            startActivity(intent)
            this.finish()
        }, 1000)


    }
}