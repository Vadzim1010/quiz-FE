package com.rsschool.quiz.veiw.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.*
import com.rsschool.quiz.data.DataManager
import com.rsschool.quiz.model.PageState
import com.rsschool.quiz.veiw.ui.quiz.QuizButtonsListener
import com.rsschool.quiz.veiw.ui.result.ResultButtonsListener
import com.rsschool.quiz.veiw.ui.quiz.QuizFragment
import com.rsschool.quiz.veiw.ui.result.ResultFragment

class MainActivity : AppCompatActivity(), QuizButtonsListener,
    ResultButtonsListener {

    private val dataManager: DataManager = DataManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startQuizFragment(dataManager.getStartedPage())
    }

    private fun startQuizFragment(pageState: PageState) {
        val quizFragment = QuizFragment.newInstance(pageState)
        quizFragment.setQuizListener(this)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFragment)
        transaction.commit()
    }

    private fun startResultFragment(resultList: ArrayList<Int>) {
        val resultFragment = ResultFragment.newInstance(resultList)
        resultFragment.setStartOverButtonListener(this)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, resultFragment)
        transaction.commit()
    }

    override fun onNextButtonListener(pageState: PageState, quizSize: Int) {
        if (pageState.currentPage == quizSize) {
            startResultFragment(dataManager.getResult(pageState))
        } else startQuizFragment(dataManager.getNextPage(pageState))
    }

    override fun onBackButtonListener(pageState: PageState) {
        startQuizFragment(dataManager.getPreviousPage(pageState))
    }

    override fun onStartOverButtonListener() {
        startQuizFragment(dataManager.getStartedPage())
    }
}