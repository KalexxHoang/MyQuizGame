package com.example.myquizgame.view.login

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.mock_mvvm.data.model.User
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentLoginBinding
import com.example.myquizgame.view_model.quiz_view_model.QuizViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loginTransaction: FragmentTransaction
    private val loginQuizViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(layoutInflater)

        /**************************************
         *               Sign in              *
         **************************************/
        loginBinding.btnSignin.setOnClickListener {
            clickLogin()
        }

        /**************************************
         *         Continue with Google       *
         **************************************/
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        loginBinding.continueLayout.setOnClickListener {
            signInGoogle()
        }

        signUp()

        forgot()

        return loginBinding.root
    }

    private fun switchFragment(fragment: Fragment) {
        loginTransaction = fragmentManager?.beginTransaction()!!
        loginTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
    }

    /**************************************
     *              Handle Login          *
     **************************************/
    private fun clickLogin() {
        val email = loginBinding.edtEmail.text.toString().trim()
        val password = loginBinding.edtPassword.text.toString().trim()

        val user = User(email, password)
        login(user)
    }

    private fun login(user: User) {
        if (user.isValidEmail() && user.isValidPassword()) {
            loginQuizViewModel.setEmail(user.email)
            val homeFragment = HomeFragment()
            switchFragment(homeFragment)
        } else
            Toast.makeText(activity, "Email or Password are invalid", Toast.LENGTH_SHORT).show()
    }

    /**************************************
     *     Handle Sign in with Google     *
     **************************************/
    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null)
                updateUI(account)
        } else {
            Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                loginQuizViewModel.setEmail(getCurrentEmail())

                val homeFragment = HomeFragment()
                switchFragment(homeFragment)
            } else {
                Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentEmail(): String {
        return Firebase.auth.currentUser?.email.toString()
    }

    /**************************************
     *        Custom Sign Up String       *
     **************************************/
    private fun signUp() {
        val spannableSignUp: CharSequence = getText(R.string.sign_up)
        val spannableSignUpString = SpannableString(spannableSignUp)

        spannableSignUpString.setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            (spannableSignUp.length - 8),
            0
        )

        val clickableSpanSignUp = object : ClickableSpan() {
            override fun onClick(view: View) {
                val signUpFragment = SignUpFragment()
                switchFragment(signUpFragment)
            }
        }
        spannableSignUpString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            (spannableSignUp.length - 7),
            spannableSignUp.length,
            0
        )
        spannableSignUpString.setSpan(
            clickableSpanSignUp,
            (spannableSignUp.length - 7),
            spannableSignUp.length,
            0
        )

        loginBinding.txtSignup.movementMethod = LinkMovementMethod.getInstance()
        loginBinding.txtSignup.text = spannableSignUpString
    }

    /**************************************
     *        Custom Forgot String        *
     **************************************/
    private fun forgot() {
        val spannableForgot: CharSequence = getText(R.string.forgot)
        val spannableForgotString = SpannableString(spannableForgot)

        val clickableSpanForgot = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val resetFragment = ResetFragment()
                switchFragment(resetFragment)
            }
        }
        spannableForgotString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            spannableForgot.length,
            0
        )
        spannableForgotString.setSpan(clickableSpanForgot, 0, spannableForgot.length, 0)

        loginBinding.txtForgot.movementMethod = LinkMovementMethod.getInstance()
        loginBinding.txtForgot.text = spannableForgotString
    }
}