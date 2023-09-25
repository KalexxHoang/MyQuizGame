package com.example.myquizgame.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.mock_mvvm.data.model.Score
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentCongraBinding
import com.example.myquizgame.view.login.HomeFragment
import com.example.myquizgame.view_model.quiz_view_model.QuizViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CongraFragment : Fragment() {
    private lateinit var congraBinding: FragmentCongraBinding
    private val congraQuizViewModel: QuizViewModel by activityViewModels()

    private lateinit var congraTransaction: FragmentTransaction
    private lateinit var dataBase: DatabaseReference
    private var correctAnswer: Int? = 0
    private var wrongAnswer: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        congraBinding = FragmentCongraBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return congraBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setScore()

        congraBinding.btnExit.setOnClickListener {
            switchToHome()
            putResult()
        }

        congraBinding.btnPlayAgain.setOnClickListener {
            switchToQuiz()
        }
    }

    private fun setScore() {
        correctAnswer = congraQuizViewModel.getCorrectAns().value
        wrongAnswer = congraQuizViewModel.getWrongAns().value

        congraBinding.correctAns.text = "Correct Answer:     $correctAnswer"
        congraBinding.wrongAns.text = "Wrong Answer:     $wrongAnswer"

    }

    private fun switchFragment(fragment: Fragment) {
        congraTransaction = fragmentManager?.beginTransaction()!!
        congraTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
    }

    private fun switchToHome() {
        val homeFragment = HomeFragment()
        switchFragment(homeFragment)
    }

    private fun switchToQuiz() {
        val quizFragment = QuizFragment()
        switchFragment(quizFragment)
    }

    private fun putResult() {
        dataBase = FirebaseDatabase.getInstance().getReference("Result")

        val email = congraQuizViewModel.getEmail().value
        val scoreID = dataBase.push().key!!

        dataBase.child(scoreID).setValue(Score(scoreID, email, correctAnswer, wrongAnswer))
            .addOnCompleteListener {
                Toast.makeText(activity, "Add successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(activity, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}