package com.example.myquizgame.data.repository.data_repository

import com.example.mock_mvvm.data.model.Question
import com.example.myquizgame.view_model.quiz_view_model.QuizViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DataRepository(private val quizViewModel: QuizViewModel): RepositoryService {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var questionList: ArrayList<Question>

    override fun getQuestions() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Questions")
        questionList = arrayListOf()

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                questionList.clear()
                if (snapshot.exists()) {
                    val questionList = arrayListOf<Question>()
                    for (quesSnap in snapshot.children) {
                        val question = quesSnap.getValue(Question::class.java)
                        questionList.add(question!!)
                    }
                    quizViewModel.fetchQuestions(questionList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}