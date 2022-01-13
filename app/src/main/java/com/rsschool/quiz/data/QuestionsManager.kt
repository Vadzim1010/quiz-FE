package com.rsschool.quiz.data

import com.rsschool.quiz.model.QuestionItem

class QuestionsManager {
    fun getQuestion(id: Int): QuestionItem {
        return questionItems[id]
    }

    fun size(): Int {
        return questionItems.size - 1
    }

    fun getSelectedAnswer(currentPage: Int, selectedAnswer: Int): String {
        return when (selectedAnswer) {
            1 -> questionItems[currentPage].answer1
            2 -> questionItems[currentPage].answer2
            3 -> questionItems[currentPage].answer3
            4 -> questionItems[currentPage].answer4
            5 -> questionItems[currentPage].answer5
            else -> {
                "ОШИБКА"
            }
        }
    }

    private val questionItems = listOf(
        QuestionItem(1, "2+2=?", "1", "2", "3", "4", "6", 4),
        QuestionItem(2, "2+3=?", "1", "2", "3", "4", "5", 5),
        QuestionItem(3, "2+4=?", "1", "6", "3", "4", "5", 2),
        QuestionItem(4, "2+5=?", "1", "2", "7", "4", "5", 3),
        QuestionItem(5, "2+6=?", "8", "2", "3", "4", "5", 1),
    )
}