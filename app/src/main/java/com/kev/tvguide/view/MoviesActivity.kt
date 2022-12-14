package com.kev.tvguide.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.tvguide.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {
	private lateinit var navController: NavController
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_movies)


		val navHostFragment = supportFragmentManager.findFragmentById(R.id.moviesNavHostFragment) as NavHostFragment
		navController = navHostFragment.navController

		val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
		bottomNavigationView.setupWithNavController(navController)

		navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
			if (nd.id == R.id.movieDetailsFragment) {
				bottomNavigationView.visibility = View.GONE
			} else {
				bottomNavigationView.visibility = View.VISIBLE
			}

	}

}
}