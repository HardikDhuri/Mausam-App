package com.example.mausam.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.example.mausam.R
import com.example.mausam.api.Location
import com.example.mausam.database.SetupData
import com.example.mausam.database.db.WeatherDatabase
import com.example.mausam.databinding.ActivityMausamMainBinding
import com.example.mausam.ui.fragments.home.HomeFragment
import com.example.mausam.ui.fragments.profile.ProfileFragment
import com.example.mausam.ui.fragments.search.SearchFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MausamMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMausamMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var  sharedPref: SharedPreferences
    private lateinit var database: WeatherDatabase
    private lateinit var location: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth
        sharedPref = this.getSharedPreferences("coords", Context.MODE_PRIVATE)
        database = WeatherDatabase.getDatabase(this)

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

        val setupData = SetupData(this)
            setupData.setup()
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
