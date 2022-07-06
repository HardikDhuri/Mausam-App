package com.example.mausam.ui.fragments.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mausam.R
import com.example.mausam.ui.LoginActivity
import com.example.mausam.ui.MausamMainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        setUserProfile(currentUser)

        logout_btn.setOnClickListener {
            auth.signOut()
            redirectToLogin()
        }
    }

    private fun setUserProfile(user: FirebaseUser?) {
        val name = user?.displayName
        val email = user?.email
        val photoUrl = user?.photoUrl

        name_profile_frag.text = name
        email_profile_frag.text = email

        activity?.let {
            Glide.with(it).load(photoUrl).into(user_profile_picture_image_view)
        }
    }

    private fun redirectToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }


}