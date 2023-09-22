package com.example.myquizgame.create_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mock_mvvm.data.model.Question
import com.example.myquizgame.R
import com.example.myquizgame.databinding.ActivityCreateDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateDataActivity : AppCompatActivity() {
    private lateinit var createBinding: ActivityCreateDataBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createBinding = ActivityCreateDataBinding.inflate(layoutInflater)
        setContentView(createBinding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Questions")
        createBinding.btnAdd.setOnClickListener {
            addData()
        }
    }

    private fun addData() {
        val ques = createBinding.ques.text.toString()
        val ans1 = createBinding.ans1.text.toString()
        val ans2 = createBinding.ans2.text.toString()
        val ans3 = createBinding.ans3.text.toString()
        val ans4 = createBinding.ans4.text.toString()
        val trueAns = createBinding.trueAns.text.toString()

        val quesID = databaseReference.push().key!!

        val question = Question(quesID, ques, ans1, ans2, ans3, ans4, trueAns)

        databaseReference.child(quesID).setValue(question)
            .addOnCompleteListener {
                Toast.makeText(this, "Add successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}