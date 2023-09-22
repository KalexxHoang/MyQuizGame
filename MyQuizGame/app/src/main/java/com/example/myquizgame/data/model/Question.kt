package com.example.mock_mvvm.data.model

data class Question(
    var quesID: String? = "",
    var question: String = "",
    var ans1: String = "",
    var ans2: String = "",
    var ans3: String = "",
    var ans4: String= "",
    var trueAns: String= ""
)