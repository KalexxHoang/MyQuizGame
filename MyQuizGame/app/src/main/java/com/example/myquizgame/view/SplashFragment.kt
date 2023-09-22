package com.example.myquizgame.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.myquizgame.R

class SplashFragment : Fragment() {
    private lateinit var splashTransaction: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        switchFragment()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun switchFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            splashTransaction = fragmentManager?.beginTransaction()!!

            val loginFragment = LoginFragment()
            splashTransaction.replace(R.id.container, loginFragment).addToBackStack(null).commit()
        }, 5000)
    }
}