package com.example.myquizgame.view_model.score_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mock_mvvm.data.model.Question
import com.example.mock_mvvm.data.model.Score
import com.example.myquizgame.data.model.UserResult
import com.example.myquizgame.data.repository.score_repository.ScoreRepository
import com.example.myquizgame.data.repository.score_repository.ScoreRepositoryService

class ScoreViewModel: ViewModel(), ScoreViewModelService {
    private val scoreList = MutableLiveData<ArrayList<Score>>()
    private val scoreRepository: ScoreRepositoryService = ScoreRepository(this)

    private val email = MutableLiveData<String>()
    private val userResult = MutableLiveData<UserResult>()

    init {
        scoreRepository.getScores()
    }

    override fun fetchScoreList(scores: ArrayList<Score>) {
        this.scoreList.value = scores
    }

    override fun getScoreList(): LiveData<ArrayList<Score>> = scoreList

    override fun setEmail(email: String) {
        this.email.value = email
    }

    override fun setScore(correctAns: Int, wrongAns: Int) {
        val result = UserResult(email.value, correctAns, wrongAns)

        this.userResult.value = result
    }

    override fun getResult(): LiveData<UserResult> = userResult
}