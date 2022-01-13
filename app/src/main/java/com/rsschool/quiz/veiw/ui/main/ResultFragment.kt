package com.rsschool.quiz.veiw.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.data.QuestionsManager
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.veiw.ui.listeners.StartOverButtonListener

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private var onStartOverButtonListener: StartOverButtonListener? = null
    private val questionsManager = QuestionsManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultList = arguments?.getIntegerArrayList(RESULT_LIST)

        binding.result.setText(R.string.result)

        binding.startOverButton.setOnClickListener {
            onStartOverButtonListener?.onStartOverButtonListener()
        }
        binding.shareButton.setOnClickListener {
            onShareButtonClicked(resultList)
        }
        binding.closeButton.setOnClickListener {
            activity?.finish()
        }
    }

    private fun onShareButtonClicked(resultList: ArrayList<Int>?) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quiz Results")
        intent.putExtra(Intent.EXTRA_TEXT, resultList?.let { getResult(it) })
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share"))
    }

    fun setStartOverButtonListener(listener: StartOverButtonListener) {
        onStartOverButtonListener = listener
    }

    private fun getResult(resultList: ArrayList<Int>): String {
        var resultPercent = 0
        var result = ""
        var headline = ""
        for (i in 0 until resultList.size) {
            if (resultList[i] == questionsManager.getQuestion(i).correctAnswer) {
                resultPercent += 1
            }
            result += "${i + 1}) ${questionsManager.getQuestion(i).question}\n" +
                    "Your answer: ${questionsManager.getSelectedAnswer(i, resultList[i])}\n\n"
        }
        headline = "Your result: $resultPercent/${resultList.size}\n\n"
        result = headline + result
        return result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(resultList: ArrayList<Int>): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putIntegerArrayList(RESULT_LIST, resultList)
            fragment.arguments = args
            return fragment
        }

        private const val RESULT_LIST = "RESULT_LIST"
    }
}