package com.labactivity.synthronize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.labactivity.synthronize.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectNavigation(binding.groupsBtn.id)
        replaceFragment(GroupSelectionFragment(binding))




        //BOTTOM NAVIGATION BUTTONS
        binding.groupsBtn.setOnClickListener {
            selectNavigation(binding.groupsBtn.id)
            binding.toolbarTitleTV.text = "GROUPS"
            replaceFragment(GroupSelectionFragment(binding))
        }

        binding.exploreBtn.setOnClickListener {
            selectNavigation(binding.exploreBtn.id)
            binding.toolbarTitleTV.text = "EXPLORE"
            replaceFragment(ExploreFragment(binding))
        }

        binding.notificationBtn.setOnClickListener {
            selectNavigation(binding.notificationBtn.id)
            binding.toolbarTitleTV.text = "NOTIFICATIONS"
            replaceFragment(NotificationFragment(binding))
        }

        binding.profileBtn.setOnClickListener {
            selectNavigation(binding.profileBtn.id)
            binding.toolbarTitleTV.text = "PROFILE"
            replaceFragment(ProfileFragment(binding))
        }
        binding.chatBtn.setOnClickListener {
            selectNavigation(binding.chatBtn.id)
            replaceFragment(ChatFragment(binding))
        }



    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.mainFrameLayout.id, fragment)
        fragmentTransaction.commit()
    }

    //Changes navigation's buttons states
    private fun selectNavigation(btnID:Int) {
        binding.exploreBtn.setBackgroundResource(R.drawable.explore_not_selected)
        binding.notificationBtn.setBackgroundResource(R.drawable.notifications_not_selected)
        binding.groupsBtn.setBackgroundResource(R.drawable.groups_not_selected)
        binding.profileBtn.setBackgroundResource(R.drawable.profile_not_selected)
        binding.chatBtn.setBackgroundResource(R.drawable.chat_not_selected)
        when (btnID) {
            binding.exploreBtn.id -> {
                binding.exploreBtn.setBackgroundResource(R.drawable.explore_selected)
            }
            binding.notificationBtn.id -> {
                binding.notificationBtn.setBackgroundResource(R.drawable.notifications_selected)
            }
            binding.groupsBtn.id -> {
                binding.groupsBtn.setBackgroundResource(R.drawable.groups_selected)
            }
            binding.profileBtn.id -> {
                binding.profileBtn.setBackgroundResource(R.drawable.profile_selected)
            }
            binding.chatBtn.id -> {
                binding.chatBtn.setBackgroundResource(R.drawable.chat_selected)

            }
        }
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