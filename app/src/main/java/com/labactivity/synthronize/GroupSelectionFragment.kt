package com.labactivity.synthronize

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.labactivity.synthronize.databinding.ActivityMainBinding
import com.labactivity.synthronize.databinding.FragmentExploreBinding
import com.labactivity.synthronize.databinding.FragmentGroupBinding
import com.labactivity.synthronize.databinding.FragmentGroupSelectionBinding
class GroupSelectionFragment : Fragment {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentGroupSelectionBinding
    private lateinit var mainBinding: ActivityMainBinding

    constructor() : super()
    constructor(mainBinding: ActivityMainBinding): super() {
        this.mainBinding = mainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding.toolbarTitleTV.text = "GROUPS"
    }
}