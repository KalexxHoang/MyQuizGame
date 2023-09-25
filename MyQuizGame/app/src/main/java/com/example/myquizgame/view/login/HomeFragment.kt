package com.example.myquizgame.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentHomeBinding
import com.example.myquizgame.view.quiz.QuizFragment

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeTransaction: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)

        Toast.makeText(activity, "Welcome to Quiz Game", Toast.LENGTH_SHORT).show()

        homeBinding.btnStart.setOnClickListener {
            clickStart()
        }

        homeBinding.btnSignOut.setOnClickListener {
            clickSignOut()
        }

        // Inflate the layout for this fragment
        return homeBinding.root
    }

    private fun clickStart() {
        val quizFragment = QuizFragment()
        switchFragment(quizFragment)
    }

    private fun clickSignOut() {
        val loginFragment = LoginFragment()
        switchFragment(loginFragment)

        Toast.makeText(activity, "Sign out is successful", Toast.LENGTH_LONG).show()
    }

    private fun switchFragment(fragment: Fragment) {
        homeTransaction = fragmentManager?.beginTransaction()!!
        homeTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
    }
}