package com.example.myquizgame.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.mock_mvvm.data.model.User
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var signUpBinding: FragmentSignUpBinding
    private lateinit var signUpTransaction: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpBinding = FragmentSignUpBinding.inflate(layoutInflater)

        signUpBinding.btnSignUp.setOnClickListener {
            clickSignUp()
        }

        signUpBinding.btnBack.setOnClickListener {
            back()
        }

        // Inflate the layout for this fragment
        return signUpBinding.root
    }

    private fun clickSignUp() {
        val email = signUpBinding.edtEmail.text.toString().trim()
        val password = signUpBinding.edtPassword.text.toString().trim()

        val user = User(email,password)
        login(user)
    }

    private fun login(user: User) {
        if (user.isValidEmail() && user.isValidPassword()) {
            back()
            Toast.makeText(activity, "Sign up successful", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(activity, "Email or Password are invalid", Toast.LENGTH_SHORT).show()
    }

    private fun back() {
        signUpTransaction = fragmentManager?.beginTransaction()!!

        val loginFragment = LoginFragment()
        signUpTransaction.replace(R.id.container, loginFragment).addToBackStack(null).commit()
    }
}