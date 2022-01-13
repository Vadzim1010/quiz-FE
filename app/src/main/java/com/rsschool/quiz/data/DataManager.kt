package com.rsschool.quiz.data

import com.rsschool.quiz.model.PageState


class DataManager {

    private var currentPage = 0
    private var pageStateList = mutableListOf<PageState>()
    private var resultList = arrayListOf<Int>()

    fun getNextPage(checkedRadioButton: PageState): PageState {
        saveState(checkedRadioButton)
        currentPage += 1
        return if (currentPage >= pageStateList.size) {
            PageState(currentPage, -1)
        } else pageStateList[currentPage]
    }

    fun getPreviousPage(pageState: PageState): PageState {
        saveState(pageState)
        currentPage -= 1
        return pageStateList[currentPage]
    }

    fun getStartedPage(): PageState {
        clearAll()
        return PageState(0, -1)
    }

    fun getResult(pageState: PageState): ArrayList<Int> {
        saveState(pageState)
        for (page in pageStateList) {
            resultList.add(page.selectedAnswer)
        }
        return resultList
    }

    private fun saveState(pageState: PageState) {
        if (pageStateList.size <= currentPage) {
            pageStateList.add(pageState)
        } else pageStateList[currentPage] = pageState
    }

    private fun clearAll() {
        pageStateList.clear()
        currentPage = 0
    }
}