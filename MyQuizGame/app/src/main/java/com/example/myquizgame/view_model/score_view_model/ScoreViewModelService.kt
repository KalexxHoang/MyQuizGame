package com.example.myquizgame.view_model.score_view_model

import androidx.lifecycle.LiveData
import com.example.mock_mvvm.data.model.Score
import com.example.myquizgame.data.model.UserResult

interface ScoreViewModelService {
    fun fetchScoreList(scores: ArrayList<Score>)

    fun getScoreList(): LiveData<ArrayList<Score>>

    fun setEmail(email: String)

    fun setScore(correctAns: Int, wrongAns: Int)

    fun getResult(): LiveData<UserResult>
}