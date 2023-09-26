package com.example.myquizgame.view_model.quiz_view_model

import androidx.lifecycle.LiveData
import com.example.mock_mvvm.data.model.Question
import com.example.myquizgame.data.model.UserResult

interface QuizViewModelService {
    fun fetchQuestions(question: ArrayList<Question>)

    fun getQuestionList(): LiveData<ArrayList<Question>>
}