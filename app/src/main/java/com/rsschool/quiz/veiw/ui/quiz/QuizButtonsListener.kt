package com.rsschool.quiz.veiw.ui.quiz

import com.rsschool.quiz.model.PageState

interface QuizButtonsListener {
    fun onBackButtonListener(pageState : PageState)

    fun onNextButtonListener(pageState : PageState, quizSize: Int)
}