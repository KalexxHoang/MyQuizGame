package com.example.myquizgame.view_model.quiz_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mock_mvvm.data.model.Question
import com.example.mock_mvvm.data.model.Score
import com.example.myquizgame.data.model.UserResult
import com.example.myquizgame.data.repository.data_repository.DataRepository
import com.example.myquizgame.data.repository.data_repository.RepositoryService

class QuizViewModel(): ViewModel(), QuizViewModelService {
    private val questionList = MutableLiveData<ArrayList<Question>>()
    private val dataRepository: RepositoryService = DataRepository(this)

    init {
        dataRepository.getQuestions()
    }

    override fun fetchQuestions(question: ArrayList<Question>) {
        questionList.value = question
    }

    override fun getQuestionList(): LiveData<ArrayList<Question>> = questionList
}