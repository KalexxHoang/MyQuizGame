package com.example.myquizgame.data.repository.score_repository


import com.example.mock_mvvm.data.model.Score
import com.example.myquizgame.view_model.score_view_model.ScoreViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ScoreRepository(private val scoreViewModel: ScoreViewModel): ScoreRepositoryService {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var scoreList: ArrayList<Score>

    override fun getScores() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Score")
        scoreList = arrayListOf()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scoreList.clear()
                if (snapshot.exists()) {
                    for (scoreSnap in snapshot.children) {
                        val score = scoreSnap.getValue(Score::class.java)
                        scoreList.add(score!!)
                    }
                    scoreList.sortBy {
                        it.userResult?.wrongAns
                    }
                    scoreViewModel.fetchScoreList(scoreList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}