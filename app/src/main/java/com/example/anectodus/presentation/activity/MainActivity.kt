package com.example.anectodus.presentation.activity

import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.anectodus.R
import com.example.anectodus.databinding.ActivityMainBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as JokeApp).component
    }
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        launchNavigation()

    }

    private fun launchNavigation(){
        val navView : BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_container)

        supportActionBar?.hide()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.addJokeFragment,
                R.id.accountFragment
            )
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)
        autoRegistration(navController)
    }


    private fun autoRegistration(navController : NavController){
        val user = firebaseAuth.currentUser
        if(user !=null){
            navController.navigate(R.id.homeFragment)
        }else{
            navController.navigate(R.id.authorizationFragment)
        }
    }

}