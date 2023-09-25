package com.example.myquizgame.view_model.quiz_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mock_mvvm.data.model.Question
import com.example.myquizgame.data.repository.data_repository.DataRepository

class QuizViewModel(): ViewModel(), QuizViewModelService {
    private val questionList = MutableLiveData<ArrayList<Question>>()
    private val dataRepository = DataRepository(this)

    private val email = MutableLiveData<String>()

    private val correctAns = MutableLiveData<Int>()
    private val wrongAns = MutableLiveData<Int>()

    init {
        dataRepository.getQuestions()
    }

    override fun fetchQuestions(question: ArrayList<Question>) {
        questionList.value = question
    }

    override fun getQuestionList(): LiveData<ArrayList<Question>> = questionList

    override fun setEmail(email: String) {
        this.email.value = email
    }

    override fun getEmail(): LiveData<String> = email

    override fun setScore(correctAns: Int, wrongAns: Int) {
        this.correctAns.value = correctAns
        this.wrongAns.value = wrongAns
    }

    override fun getCorrectAns(): LiveData<Int> = correctAns
    override fun getWrongAns(): LiveData<Int> = wrongAns
}