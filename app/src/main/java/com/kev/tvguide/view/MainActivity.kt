package com.kev.tvguide.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.tvguide.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var navController: NavController
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		val navHostFragment = supportFragmentManager.findFragmentById(R.id.moviesNavHostFragment) as NavHostFragment
		navController = navHostFragment.navController

		val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
		bottomNavigationView.setupWithNavController(navController)
	}

}