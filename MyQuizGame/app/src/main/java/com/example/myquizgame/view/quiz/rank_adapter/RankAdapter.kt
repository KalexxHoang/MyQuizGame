package com.example.myquizgame.view.quiz.rank_adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mock_mvvm.data.model.Score
import com.example.myquizgame.R

class RankAdapter(val activity: Activity, val scoreList: List<Score>): ArrayAdapter<Score>(activity, R.layout.list_score_item) {
    override fun getCount(): Int {
        return scoreList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = activity.layoutInflater.inflate(R.layout.list_score_item, parent, false)

        val rank = rowView.findViewById<TextView>(R.id.rank)
        val email = rowView.findViewById<TextView>(R.id.email)
        val correctAns = rowView.findViewById<TextView>(R.id.correct_ans)
        val wrongAns = rowView.findViewById<TextView>(R.id.wrong_ans)

        rank.text = (position + 1).toString()
        email.text = scoreList[position].userResult?.email
        correctAns.text = "Correct: ${scoreList[position].userResult?.correctAns}"
        wrongAns.text = "Wrong: ${scoreList[position].userResult?.wrongAns}"

        return rowView
    }
}