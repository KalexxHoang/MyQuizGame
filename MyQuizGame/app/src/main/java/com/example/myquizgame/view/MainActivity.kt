package com.example.myquizgame.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.myquizgame.R
import com.example.myquizgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainTransaction: FragmentTransaction
    private lateinit var mainLoginBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainLoginBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainLoginBinding.root)

        mainTransaction = supportFragmentManager.beginTransaction()

        // Add SplashFragment
        val splashFragment = SplashFragment()
        mainTransaction.replace(R.id.container, splashFragment).addToBackStack(null).commit()
    }
}