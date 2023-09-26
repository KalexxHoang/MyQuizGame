package com.example.myquizgame.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mock_mvvm.data.model.Score
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentCongraBinding
import com.example.myquizgame.view.login.HomeFragment
import com.example.myquizgame.view_model.quiz_view_model.QuizViewModel
import com.example.myquizgame.view_model.score_view_model.ScoreViewModel
import com.example.myquizgame.view_model.score_view_model.ScoreViewModelService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CongraFragment : Fragment() {
    private lateinit var congraBinding: FragmentCongraBinding
    private lateinit var congraScoreViewModel: ScoreViewModelService

    private lateinit var congraTransaction: FragmentTransaction
    private lateinit var dataBase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        congraBinding = FragmentCongraBinding.inflate(layoutInflater)

        congraScoreViewModel = ViewModelProvider(requireActivity())[ScoreViewModel::class.java]

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

        congraBinding.btnRank.setOnClickListener {
            putResult()
            switchToRank()
        }
    }

    private fun setScore() {
        congraScoreViewModel.getResult().observe(viewLifecycleOwner, Observer {
                    congraBinding.correctAns.text = "Correct Answer:     ${it.correctAns}"
                    congraBinding.wrongAns.text = "Wrong Answer:     ${it.wrongAns}"
        })
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

    private fun switchToRank() {
        val rankFragment = RankFragment()
        switchFragment(rankFragment)
    }

    private fun putResult() {
        dataBase = FirebaseDatabase.getInstance().getReference("Score")
        val scoreID = dataBase.push().key!!
        val userResult = congraScoreViewModel.getResult().value

        dataBase.child(scoreID).setValue(Score(scoreID, userResult))
            .addOnCompleteListener {
                Toast.makeText(activity, "Add successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(activity, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}