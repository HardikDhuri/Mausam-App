package com.example.mausam.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mausam.R
import com.example.mausam.api.Location
import com.example.mausam.api.Weather
import com.example.mausam.api.data.current_weather_data.CurrentWeatherData
import com.example.mausam.databinding.ActivityMausamMainBinding
import com.example.mausam.others.Contants
import com.example.mausam.others.Utility.isLocationInitialized
import com.example.mausam.ui.fragments.home.HomeFragment
import com.example.mausam.ui.fragments.profile.ProfileFragment
import com.example.mausam.ui.fragments.search.SearchFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MausamMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMausamMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var  sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth
        sharedPref = this.getSharedPreferences("coords", Context.MODE_PRIVATE)

        binding = ActivityMausamMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()



        setCurrentFragment(homeFragment)

        if (!isLocationInitialized(sharedPref)) {
            Location(this)
        }

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


}
