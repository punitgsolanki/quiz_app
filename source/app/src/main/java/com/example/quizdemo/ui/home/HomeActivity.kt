package com.example.quizdemo.ui.home

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizdemo.R
import com.example.quizdemo.data.database.entity.UserScore
import com.example.quizdemo.data.model.QuizResponse
import com.example.quizdemo.data.model.Resource
import com.example.quizdemo.data.model.Result
import com.example.quizdemo.databinding.ActivityHomeBinding
import com.example.quizdemo.ui.adapter.QuizAdapter
import com.example.quizdemo.ui.base.BaseActivity
import com.example.quizdemo.ui.result.ResultActivity
import com.example.quizdemo.utils.DialogUtils
import com.example.quizdemo.utils.JLog
import com.example.quizdemo.utils.consts.AppConst
import com.example.quizdemo.utils.extensions.nullSafe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    private val viewModel: HomeViewModel by viewModels()

    // Adapter for display all the questions
    private lateinit var quizAdapter: QuizAdapter

    // List for store the all quiz questions into the local variable.
    private var quizQuestions = mutableListOf<Result>()

    companion object {
        /**
         * Start home activity
         * @param context current screen context as argument
         */
        fun callHomeActivity(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun initSetup() {
        setupRecyclerView() // Set recycler view
        viewModel.getQuizQuestions(AppConst.TOTAL_QUESTION) // Load all the questions when activity starts
        viewModel.getLastScore() // Load the last score
    }

    private fun setupRecyclerView() {
        // Initialize adapter
        quizAdapter = QuizAdapter { position, answer ->
            JLog.printLog("Question $position answered with: $answer")
        }

        // Set adapter into the the RecyclerView
        binding.rvQuizQuestions.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            setItemViewCacheSize(1000)
            adapter = quizAdapter
        }
    }

    override fun onDestroyActivity() {
    }

    override fun listeners() {
        // Set refresh button click listener
        binding.btnRefresh.setOnClickListener {
            viewModel.getQuizQuestions(AppConst.TOTAL_QUESTION)
        }

        // Set submit button click listener
        binding.btnSubmit.setOnClickListener {
            submitQuiz()
        }

        // Set try again button click listener
        binding.btnTryAgain.setOnClickListener {
            viewModel.getQuizQuestions(AppConst.TOTAL_QUESTION)
        }
    }

    override fun addObservers() {
        viewModel.status.observe(this, mObserver)
        viewModel.lastScore.observe(this, lastScoreObserver)
    }

    override fun removeObservers() {
        viewModel.status.removeObserver(mObserver)
        viewModel.lastScore.removeObserver(lastScoreObserver)
    }

    /**
     * Observer for observe the state of the screen, based on that display the loader, recycler view or error message
     */
    private val mObserver = Observer<Any> {
        when (it) {
            is Resource.Error<*> -> {
                // Error state, hide progressbar and display the snack bar
                hideProgressBar()
                showSnackBar(message = it.message.nullSafe())
                showEmptyState(true)
            }

            is Resource.Success<*> -> {
                // Success state, hide progressbar and display the all questions into the list
                hideProgressBar()
                val response = it.data as QuizResponse
                setQuizQuestions(response.results)
            }

            is Resource.Loading<*> -> {
                // State for show and hide the progressbar
                it.isLoadingShow.let { isLoading ->
                    if (isLoading.nullSafe()) {
                        showProgressBar()
                    } else {
                        hideProgressBar()
                    }
                }
            }

            is Resource.NoInternetError<*> -> {
                // No internet connection state, hide progressbar and display the error dialog with no internet connection message.
                hideProgressBar()
                DialogUtils.showInfoDialog(
                    context = this,
                    message = getString(R.string.default_internet_message)
                )
                showEmptyState(true)
            }
        }
    }

    /**
     * Observer for observe the last score value and display into the TextView
     */
    private val lastScoreObserver = Observer<UserScore?> { userScore ->
        userScore?.let {
            binding.tvLastScore.apply {
                "Last Score: ${it.score}/${it.totalQuestions}".also { text = it }
                visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun submitQuiz() {
        // Get answered list in Map<Int, String>
        val userAnswers = quizAdapter.getUserAnswers()

        // Check validation, is all questions answer are given or not.
        if (isValidate(userAnswers)) {
            // Calculate total score
            val score = calculateTotalScore(userAnswers)

            // Save score into the local database
            viewModel.saveUserScore(UserScore(score = score, totalQuestions = quizQuestions.size))

            // Navigate to result screen
            navigateToResultActivity(score)
        }
    }

    private fun isValidate(userAnswers: Map<Int, String>): Boolean {
        if (userAnswers.size < quizQuestions.size) {
            showSnackBar(message = "Please answer all questions before submitting!")
            return false
        }

        return true
    }

    private fun calculateTotalScore(userAnswers: Map<Int, String>): Int {
        var score = 0
        userAnswers.forEach { (position, answer) ->
            if (answer == quizQuestions[position].correctAnswer) {
                score++
            }
        }
        return score
    }

    // Start result activity
    private fun navigateToResultActivity(score: Int) {
        startActivity(
            ResultActivity.callResultActivity(
                this,
                score,
                quizQuestions.size
            )
        )
    }

    /**
     * Set all the questions list into the adapter class
     */
    private fun setQuizQuestions(results: List<Result>) {
        if (results.isNotEmpty()) {
            JLog.printLog("RESULT DATA FOUND: ${results.size}")
            quizQuestions.clear()
            quizQuestions.addAll(results)
            quizAdapter.submitList(results)
            showEmptyState(false)
        } else {
            showEmptyState(true)
        }
    }

    /**
     * Show or Hide empty state view
     */
    private fun showEmptyState(show: Boolean) {
        binding.emptyStateContainer.visibility =
            if (show) android.view.View.VISIBLE else android.view.View.GONE
        binding.rvQuizQuestions.visibility =
            if (show) android.view.View.GONE else android.view.View.VISIBLE
        binding.btnSubmit.isEnabled = !show
    }

    /**
     * Show progress bar into the screen
     */
    private fun showProgressBar() {
        binding.progressBar.visibility = android.view.View.VISIBLE
    }

    /**
     * Hide progress bar from the screen.
     */
    private fun hideProgressBar() {
        binding.progressBar.visibility = android.view.View.GONE
    }
}