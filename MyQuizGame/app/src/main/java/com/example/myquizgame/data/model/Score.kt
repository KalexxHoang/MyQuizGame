package com.example.mock_mvvm.data.model

data class Score (
    var scoreID: String? = "",
    var email: String = "",
    var correctAns: Int? = 0,
    var wrongAns: Int? = 0
)