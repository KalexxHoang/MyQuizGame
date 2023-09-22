package com.example.myquizgame.view

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.mock_mvvm.data.model.Question
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentQuizBinding
import com.example.myquizgame.view_model.QuizViewModel
import com.google.firebase.database.DatabaseReference

class QuizFragment : Fragment() {
    private lateinit var quizBinding: FragmentQuizBinding
    private val quizViewModel: QuizViewModel by activityViewModels()

    private lateinit var quizTransaction: FragmentTransaction
    private lateinit var questionList: ArrayList<Question>
    private var countDownTimer: CountDownTimer? = null
    private var correctAns = 0
    private var wrongAns = 0
    private var index = 0
    private var clickState = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizBinding = FragmentQuizBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return quizBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizViewModel.getQuestionList().observe(viewLifecycleOwner, Observer {
            questionList = it
            if (questionList.isNotEmpty()) {
                beginQuiz()
            }
        })
    }

    private fun switchFragment(fragment: Fragment) {
        quizTransaction = fragmentManager?.beginTransaction()!!
        quizTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
    }

    private fun beginQuiz() {
        quizBinding.progress.visibility = View.GONE
        quizBinding.loading.visibility = View.GONE
        quizBinding.quesLayout.visibility = View.VISIBLE

        showQues()

        quizBinding.btnNext.setOnClickListener {
            if (index < questionList.size - 1) {
                clickNext()
            } else {
                showDialog()
            }
        }

        quizBinding.btnFinish.setOnClickListener {
            val homeFragment = HomeFragment()
            switchFragment(homeFragment)
        }
    }

    private fun showQues() {
        val currentQues = questionList[index]

        with(quizBinding) {
            ques.text = currentQues.question
            ans1.text = currentQues.ans1
            ans2.text = currentQues.ans2
            ans3.text = currentQues.ans3
            ans4.text = currentQues.ans4
        }

        clickState = 0
        timer()
        stateQuiz()
        checkAnswer()
    }

    private fun stateQuiz() {
        quizBinding.correctAns.text = "Correct Answer: $correctAns"
        quizBinding.wrongAns.text = "Wrong Answer: $wrongAns"
    }

    private fun timer() {
        countDownTimer?.cancel()
        countDownTimer = object: CountDownTimer(15000,1000) {
            override fun onTick(p0: Long) {
                quizBinding.time.text = "Time: ${p0/1000}"
            }

            override fun onFinish() {
                showTrueAns()
                quizBinding.time.text = "Time: 0"
                Handler(Looper.getMainLooper()).postDelayed({
                    if (index < questionList.size - 1) {
                        clickNext()
                    } else {
                        if (clickState == 0)
                            wrongAns++
                        stateQuiz()
                    }
                },2000)
            }

        }

        countDownTimer?.start()
    }

    private fun clickNext() {
        index++
        if (clickState == 0)
            wrongAns++

        defaultBackground()
        showQues()
    }

    private fun checkAnswer() {
        val trueAnswer = questionList[index].trueAns.toInt()

        val ansClickListener = listOf(
            quizBinding.ans1,
            quizBinding.ans2,
            quizBinding.ans3,
            quizBinding.ans4
        )

        for ((i, ansView) in ansClickListener.withIndex()) {
            ansView.setOnClickListener {
                val clickedAnswer = i + 1
                if (trueAnswer == clickedAnswer) {
                    ansView.setBackgroundResource(R.drawable.question_correct)
                    correctAns++
                } else {
                    ansView.setBackgroundResource(R.drawable.question_wrong)
                    wrongAns++
                }

                clickState++
                stateQuiz()
                showTrueAns()
                setDisable()
            }
        }
    }

    private fun setDisable() {
        with(quizBinding) {
            ans1.isClickable = false
            ans2.isClickable = false
            ans3.isClickable = false
            ans4.isClickable = false
        }
    }

    private fun setEnable() {
        with(quizBinding) {
            ans1.isClickable = true
            ans2.isClickable = true
            ans3.isClickable = true
            ans4.isClickable = true
        }
    }

    private fun showTrueAns() {
        val ansClickListener = listOf(
            quizBinding.ans1,
            quizBinding.ans2,
            quizBinding.ans3,
            quizBinding.ans4
        )

        ansClickListener[questionList[index].trueAns.toInt() - 1].background =
            AppCompatResources.getDrawable(requireContext(), R.drawable.question_correct)
    }

    private fun defaultBackground() {
        val ansClickListener = listOf(
            quizBinding.ans1,
            quizBinding.ans2,
            quizBinding.ans3,
            quizBinding.ans4
        )

        for (item in ansClickListener) {
            item.background = AppCompatResources.getDrawable(requireContext(), R.drawable.question)
        }

        setEnable()
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Quiz Game")
        alertDialog.setMessage("Congratulation!!!\nYou have answered all the questions. Do you want to see the result?")

        alertDialog.setNegativeButton("PLAY AGAIN") {
                dialog, which ->
            playAgain()
        }

        alertDialog.setPositiveButton("SEE RESULT") {
                dialog, which ->
            passResult()
        }

        alertDialog.show()
    }

    private fun playAgain() {
        index = 0
        wrongAns = 0
        correctAns = 0
        defaultBackground()
        showQues()
    }

    private fun passResult() {
        quizViewModel.setScore(correctAns, wrongAns)

        val congraFragment = CongraFragment()
        switchFragment(congraFragment)
    }
}