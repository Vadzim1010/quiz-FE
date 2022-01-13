package com.rsschool.quiz.veiw.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.data.QuestionsManager
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.model.PageState
import com.rsschool.quiz.model.QuestionItem
import com.rsschool.quiz.veiw.ui.listeners.BackButtonListener
import com.rsschool.quiz.veiw.ui.listeners.NextButtonListener

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val questionsManager: QuestionsManager = QuestionsManager()
    private var onNextButtonListener: NextButtonListener? = null
    private var onBackButtonListener: BackButtonListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val currentPage: Int = arguments?.getInt(CURRENT_PAGE) ?: 0

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                onBackButtonListener?.onBackButtonListener(getSelectedRadioButton(currentPage))
            }
        }
        if (currentPage != 0) {
            requireActivity().onBackPressedDispatcher.addCallback(
                this,
                callback
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val currentPage: Int = arguments?.getInt(CURRENT_PAGE) ?: 0
        setPageTheme(currentPage)
        setPageStatusBar()
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentPage: Int = arguments?.getInt(CURRENT_PAGE) ?: 0
        val checkedRadioButton: Int = arguments?.getInt(CHECKED_RADIO_BUTTON) ?: -1

        setPage(questionsManager.getQuestion(currentPage))
        setSelectedRadioButton(checkedRadioButton)

        binding.nextButton.setOnClickListener {
            onNextButtonListener?.onNextButtonListener(getSelectedRadioButton(currentPage),
                questionsManager.size())
        }
        binding.previousButton.setOnClickListener {
            onBackButtonListener?.onBackButtonListener(getSelectedRadioButton(currentPage))
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackButtonListener?.onBackButtonListener(getSelectedRadioButton(currentPage))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setPage(questionItems: QuestionItem) {
        with(binding) {
            toolbar.title = "Question ${questionItems.questionNumber}"
            question.text = questionItems.question
            optionOne.text = questionItems.answer1
            optionTwo.text = questionItems.answer2
            optionThree.text = questionItems.answer3
            optionFour.text = questionItems.answer4
            optionFive.text = questionItems.answer5
            if (questionItems.questionNumber == 1) {
                previousButton.isEnabled = false
                toolbar.navigationIcon = null
            } else if (questionItems.questionNumber == questionsManager.size() + 1) {
                nextButton.text = "SUBMIT"
            }
            radioButtonIsChecked()
        }
    }

    private fun setPageTheme(currentPage: Int) {
        when (currentPage) {
            0 -> {
                context?.setTheme(R.style.Theme_Quiz_First)
            }
            1 -> {
                context?.setTheme(R.style.Theme_Quiz_Second)
            }
            2 -> {
                context?.setTheme(R.style.Theme_Quiz_Third)
            }
            3 -> {
                context?.setTheme(R.style.Theme_Quiz_Fourth)
            }
            4 -> {
                context?.setTheme(R.style.Theme_Quiz_Fifth)
            }
        }
    }

    private fun setPageStatusBar() {
        val window = activity?.window
        val typedValue = TypedValue()
        val currentTheme = context?.theme
        currentTheme?.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
        window?.statusBarColor = typedValue.data
    }

    private fun setSelectedRadioButton(checkedRadioButton: Int) {
        with(binding) {
            when (checkedRadioButton) {
                1 -> optionOne.isChecked = true
                2 -> optionTwo.isChecked = true
                3 -> optionThree.isChecked = true
                4 -> optionFour.isChecked = true
                5 -> optionFive.isChecked = true
            }
        }
    }

    private fun getSelectedRadioButton(currentPage: Int): PageState {
        with(binding) {
            return when {
                optionOne.isChecked -> PageState(currentPage, 1)
                optionTwo.isChecked -> PageState(currentPage, 2)
                optionThree.isChecked -> PageState(currentPage, 3)
                optionFour.isChecked -> PageState(currentPage, 4)
                optionFive.isChecked -> PageState(currentPage, 5)
                else -> {
                    PageState(currentPage, -1)
                }
            }
        }
    }

    private fun radioButtonIsChecked() {
        with(binding) {
            if (radioGroup.checkedRadioButtonId == -1) {
                nextButton.isEnabled = false
            }
            radioGroup.setOnCheckedChangeListener { _, _ -> nextButton.isEnabled = true }
        }
    }

    fun setNextButtonListener(listener: NextButtonListener) {
        onNextButtonListener = listener
    }

    fun setBackButtonListener(listener: BackButtonListener) {
        onBackButtonListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(pageState: PageState): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putInt(CURRENT_PAGE, pageState.currentPage)
            args.putInt(CHECKED_RADIO_BUTTON, pageState.selectedAnswer)
            fragment.arguments = args
            return fragment
        }

        private const val CURRENT_PAGE = "CURRENT_PAGE"
        private const val CHECKED_RADIO_BUTTON = "CHECKED_RADIO_BUTTON"
    }
}