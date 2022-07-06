package com.example.mausam.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import com.example.mausam.R
import com.example.mausam.databinding.ActivityMausamMainBinding
import com.example.mausam.ui.fragments.home.HomeFragment
import com.example.mausam.ui.fragments.profile.ProfileFragment
import com.example.mausam.ui.fragments.search.SearchFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MausamMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMausamMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth


        binding = ActivityMausamMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home_menu -> {
                    setCurrentFragment(homeFragment)
                }
                R.id.search_menu -> {
                    setCurrentFragment(searchFragment)
                }
                R.id.profile_menu -> {
                    setCurrentFragment(profileFragment)
                }
            }
            true
        }


//        binding.signOutButton.setOnClickListener {
//            auth.signOut()
//            redirectToLoginActivity()
//        }
    }

    public fun redirectToLoginActivity() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            commit()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if (currentUser == null) {
//            showLoginActivity()
//        }
//    }
}