package com.labactivity.synthronize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.labactivity.synthronize.databinding.ActivitySearchBinding

class Search : AppCompatActivity() {
    private lateinit var binding:ActivitySearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchEdtTxt.requestFocus()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.searchEdtTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                searchFirestore(p0.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }
        })
    }

    private fun searchFirestore(query:String) {
        Toast.makeText(applicationContext, "You searched: $query", Toast.LENGTH_SHORT).show()
    }
}