package com.example.myquizgame.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mock_mvvm.data.model.Question
import com.example.myquizgame.data.repository.DataRepository

class QuizViewModel(): ViewModel() {
    private val questionList = MutableLiveData<ArrayList<Question>>()
    private val dataRepository = DataRepository(this)

    private val email = MutableLiveData<String>()

    private val correctAns = MutableLiveData<Int>()
    private val wrongAns = MutableLiveData<Int>()

    init {
        dataRepository.getQuestions()
    }

    fun fetchQuestions(question: ArrayList<Question>) {
        questionList.value = question
    }

    fun getQuestionList(): LiveData<ArrayList<Question>> = questionList

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun getEmail(): LiveData<String> = email

    fun setScore(correctAns: Int, wrongAns: Int) {
        this.correctAns.value = correctAns
        this.wrongAns.value = wrongAns
    }

    fun getCorrectAns(): LiveData<Int> = correctAns
    fun getWrongAns(): LiveData<Int> = wrongAns
}