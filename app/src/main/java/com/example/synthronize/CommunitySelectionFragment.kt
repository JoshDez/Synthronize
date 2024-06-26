package com.example.synthronize

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.synthronize.adapters.CommunityAdapter
import com.example.synthronize.databinding.ActivityMainBinding
import com.example.synthronize.databinding.DialogAddCommunityBinding
import com.example.synthronize.databinding.DialogCommunityCodeBinding
import com.example.synthronize.databinding.DialogCommunityPreviewBinding
import com.example.synthronize.databinding.FragmentCommunitySelectionBinding
import com.example.synthronize.model.CommunityModel
import com.example.synthronize.utils.AppUtil
import com.example.synthronize.utils.DialogUtil
import com.example.synthronize.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FieldValue
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import java.util.logging.Handler

class CommunitySelectionFragment(private val mainBinding: ActivityMainBinding, private val fragmentManager: FragmentManager) : Fragment() {
    private lateinit var binding: FragmentCommunitySelectionBinding
    private lateinit var dialogBinding: DialogAddCommunityBinding
    private lateinit var communityAdapter: CommunityAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommunitySelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding.toolbarTitleTV.text = "COMMUNITIES"

        //If the fragment is added
        if (isAdded){
            context = requireContext()

            //checks if context is initialized
            if (::context.isInitialized){

                // Initialize RecyclerView and adapter
                recyclerView = binding.groupSelectionRV
                recyclerView.layoutManager = LinearLayoutManager(context)

                // Set up Add Group FAB
                binding.addGroupFab.setOnClickListener {
                    dialogBinding = DialogAddCommunityBinding.inflate(layoutInflater)
                    val dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(ViewHolder(dialogBinding.root))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 700, 50, 700)
                        .setCancelable(true)
                        .create()

                    //set dialog on click listeners
                    dialogBinding.createNewCommunityBtn.setOnClickListener {
                        val intent = Intent(context, CreateCommunity::class.java)
                        startActivity(intent)
                        dialogPlus.dismiss()
                    }

                    dialogBinding.joinCommunityViaCodeBtn.setOnClickListener {
                        //CREATES NEW DIALOG FOR COMMUNITY CODE
                        val codeDialogBinding = DialogCommunityCodeBinding.inflate(layoutInflater)
                        val codeDialogPlus = DialogPlus.newDialog(context)
                            .setContentHolder(ViewHolder(codeDialogBinding.root))
                            .setCancelable(true)
                            .setMargin(50, 800, 50, 700)
                            .create()

                        //confirm button
                        codeDialogBinding.confirmBtn.setOnClickListener {
                            val code = codeDialogBinding.codeEdtTxt.text.toString()
                            if (code.isNotEmpty()){
                                FirebaseUtil().retrieveAllCommunityCollection().whereEqualTo("communityCode", code).get().addOnSuccessListener {documents ->
                                    for (document in documents){
                                        val communityModel = document.toObject(CommunityModel::class.java)
                                        DialogUtil().openCommunityPreviewDialog(context, layoutInflater, communityModel)
                                        codeDialogPlus.dismiss()
                                    }
                                }
                            } else {
                                codeDialogPlus.dismiss()
                            }
                        }
                        dialogPlus.dismiss()
                        android.os.Handler().postDelayed({
                            codeDialogPlus.show()
                        }, 500)
                    }

                    dialogBinding.searchCommunityBtn.setOnClickListener {
                        val intent = Intent(context, Search::class.java)
                        intent.putExtra("searchInCategory", "communities")
                        startActivity(intent)
                        dialogPlus.dismiss()
                    }

                    dialogPlus.show()

                }

                // Fetch groups from Firestore
                setupRecyclerView()
            }

        }
    }

    private fun setupRecyclerView() {
        //query firestore
        val communityQuery = FirebaseUtil().retrieveAllCommunityCollection()
                .whereArrayContains("communityMembers", FirebaseUtil().currentUserUid())

        //set options for firebase ui
        val options: FirestoreRecyclerOptions<CommunityModel> =
             FirestoreRecyclerOptions.Builder<CommunityModel>().setQuery(communityQuery, CommunityModel::class.java).build()

        communityAdapter = CommunityAdapter(mainBinding, fragmentManager, context, options)
        recyclerView.adapter = communityAdapter
        communityAdapter.startListening()
    }

    override fun onStart() {
        super.onStart()
        if (::communityAdapter.isInitialized)
            communityAdapter.startListening()
    }
    override fun onResume() {
        super.onResume()
        if (::communityAdapter.isInitialized)
            communityAdapter.notifyDataSetChanged()
    }


    override fun onStop() {
        super.onStop()
        if (::communityAdapter.isInitialized)
            communityAdapter.stopListening()
    }


}