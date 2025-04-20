package com.example.quizdemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizdemo.data.model.Result
import com.example.quizdemo.databinding.ItemQuizCardBinding
import com.example.quizdemo.utils.extensions.nullSafe

class QuizAdapter(private val onAnswerSelected: (Int, String) -> Unit) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    private val quizQuestions = mutableListOf<Result>()
    private val quizAnswers = mutableMapOf<Int, List<String>>()
    private val userAnswers = mutableMapOf<Int, String>()

    fun submitList(questions: List<Result>) {
        quizQuestions.clear()
        quizQuestions.addAll(questions)
        updateQuizOptions()
        notifyDataSetChanged()
    }

    fun getUserAnswers(): Map<Int, String> = userAnswers

    fun clearUserAnswers() {
        userAnswers.clear()
        updateQuizOptions()
    }

    fun getAnsweredCount(): Int = userAnswers.size

    private fun updateQuizOptions() {
        quizAnswers.clear()
        quizQuestions.forEachIndexed { index, question ->
            quizAnswers[index] = mutableListOf<String>().apply {
                add(question.correctAnswer)
                addAll(question.incorrectAnswers)
                shuffle()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ItemQuizCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(quizQuestions[holder.adapterPosition], holder.adapterPosition)
    }

    override fun getItemCount(): Int = quizQuestions.size

    inner class QuizViewHolder(private val binding: ItemQuizCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quizItem: Result, position: Int) {
            with(binding) {
                // Set question number
                "Q${position + 1}".also { tvQuestionNumber.text = it }

                // Decode HTML entities and set the question
                tvQuestion.text = HtmlCompat.fromHtml(
                    quizItem.question.nullSafe(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )

                // Set category
                tvCategory.text = quizItem.category.nullSafe()

                // Create a shuffled list of all answers
                val allAnswers = quizAnswers[position] ?: listOf()

                // Map to store the RadioButton for each answer
                val optionButtons = mapOf(
                    0 to rbOption1,
                    1 to rbOption2,
                    2 to rbOption3,
                    3 to rbOption4
                )

                // Set up options
                allAnswers.forEachIndexed { index, answer ->
                    if (index < optionButtons.size) {
                        val optionButton = optionButtons[index]
                        optionButton?.apply {
                            text = HtmlCompat.fromHtml(answer, HtmlCompat.FROM_HTML_MODE_LEGACY)
                            visibility = View.VISIBLE
                            isChecked = userAnswers[position] == answer

                            setOnClickListener {
                                userAnswers[position] = answer
                                onAnswerSelected(position, answer)
                            }
                        }
                    }
                }

                for (i in allAnswers.size until optionButtons.size) {
                    optionButtons[i]?.visibility = View.GONE
                }
            }
        }
    }
}