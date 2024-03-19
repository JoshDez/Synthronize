package com.labactivity.synthronize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.get
import com.labactivity.synthronize.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = binding.groupToolbar
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.groups_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.createNewGroup -> {
                Toast.makeText(this, "Create New Group", Toast.LENGTH_SHORT).show()
            }
            R.id.deleteGroup -> {
                Toast.makeText(this, "Delete Group", Toast.LENGTH_SHORT).show()
            }
            R.id.settings -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}