package com.example.myquizgame.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentRankBinding
import com.example.myquizgame.view.login.HomeFragment
import com.example.myquizgame.view.quiz.rank_adapter.RankAdapter
import com.example.myquizgame.view_model.score_view_model.ScoreViewModel
import com.example.myquizgame.view_model.score_view_model.ScoreViewModelService

class RankFragment : Fragment() {
    private lateinit var rankBinding: FragmentRankBinding
    private lateinit var rankTransaction: FragmentTransaction
    private lateinit var rankScoreViewModel: ScoreViewModelService
    private lateinit var rankAdapter: RankAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rankBinding = FragmentRankBinding.inflate(layoutInflater)
        rankScoreViewModel = ViewModelProvider(requireActivity())[ScoreViewModel::class.java]

        // Inflate the layout for this fragment
        return rankBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rankScoreViewModel.getScoreList().observe(viewLifecycleOwner, Observer {scoreList ->
            rankAdapter = RankAdapter(requireActivity(), scoreList)
            rankBinding.scoreList.adapter = rankAdapter
        })

        rankBinding.btnExit.setOnClickListener {
            switchToHome()
        }
    }

    private fun switchToHome() {
        val homeFragment = HomeFragment()
        rankTransaction = fragmentManager?.beginTransaction()!!
        rankTransaction.replace(R.id.container, homeFragment).addToBackStack(null).commit()
    }
}