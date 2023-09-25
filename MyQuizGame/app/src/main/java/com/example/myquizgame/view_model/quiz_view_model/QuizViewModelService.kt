package com.example.myquizgame.view_model.quiz_view_model

import androidx.lifecycle.LiveData
import com.example.mock_mvvm.data.model.Question

interface QuizViewModelService {
    fun fetchQuestions(question: ArrayList<Question>)

    fun getQuestionList(): LiveData<ArrayList<Question>>

    fun setEmail(email: String)

    fun getEmail(): LiveData<String>

    fun setScore(correctAns: Int, wrongAns: Int)

    fun getCorrectAns(): LiveData<Int>

    fun getWrongAns(): LiveData<Int>
}