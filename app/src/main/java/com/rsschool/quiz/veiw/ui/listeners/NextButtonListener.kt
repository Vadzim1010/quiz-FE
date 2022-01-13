package com.rsschool.quiz.veiw.ui.listeners

import com.rsschool.quiz.model.PageState

interface NextButtonListener {
    fun onNextButtonListener(pageState : PageState, quizSize: Int)
}