package com.example.myquizgame.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentResetBinding
import com.google.firebase.auth.FirebaseAuth

class ResetFragment : Fragment() {
    private lateinit var resetBinding: FragmentResetBinding
    private lateinit var resetTransaction: FragmentTransaction
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resetBinding = FragmentResetBinding.inflate(layoutInflater)

        resetBinding.btnBack.setOnClickListener {
            back()
        }

        resetBinding.btnReset.setOnClickListener {
            resetPassword()
        }

        // Inflate the layout for this fragment
        return resetBinding.root
    }

    private fun resetPassword() {
        val email = resetBinding.edtEmail.text.toString().trim()

        if (isValidEmail(email)) {
            auth = FirebaseAuth.getInstance()
            auth.sendPasswordResetEmail(email)
            Toast.makeText(activity, "Link was sent!!!", Toast.LENGTH_SHORT).show()
            Toast.makeText(activity, "Please check your email!!!", Toast.LENGTH_SHORT).show()
            back()
        } else
            Toast.makeText(activity, "Email is invalid!!!", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun back() {
        resetTransaction = fragmentManager?.beginTransaction()!!

        val loginFragment = LoginFragment()
        resetTransaction.replace(R.id.container, loginFragment).addToBackStack(null).commit()
    }
}