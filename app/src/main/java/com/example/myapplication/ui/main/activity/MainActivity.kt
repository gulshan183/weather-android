package com.example.myapplication.ui.main.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //init Navigation Host fragment to accommodate the navigation between all the fragments
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar with Navigation component
        navController = navHostFragment.navController
        setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        navHostFragment.childFragmentManager.fragments.forEach { fragment ->
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

}
