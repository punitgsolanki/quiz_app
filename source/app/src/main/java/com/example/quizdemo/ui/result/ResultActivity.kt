package com.example.quizdemo.ui.result

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.quizdemo.databinding.ActivityResultsBinding
import com.example.quizdemo.ui.base.BaseActivity
import com.example.quizdemo.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : BaseActivity<ActivityResultsBinding>() {
    override fun getViewBinding() = ActivityResultsBinding.inflate(layoutInflater)

    private val viewModel: ResultViewModel by viewModels()

    companion object {
        private const val EXTRA_SCORE = "extra_score"
        private const val EXTRA_TOTAL = "extra_total"

        /**
         * Start result activity
         * @param context Current screen context
         * @param score Score of the quiz game
         * @param totalQuestions Total number of questions
         */
        fun callResultActivity(context: Context, score: Int, totalQuestions: Int): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(EXTRA_SCORE, score)
                putExtra(EXTRA_TOTAL, totalQuestions)
            }
        }
    }

    override fun initSetup() {
        setupToolbar()
        loadScoreData()
    }

    override fun onDestroyActivity() {
    }

    override fun listeners() {
        // Listener for start new quiz button
        binding.btnStartNewQuiz.setOnClickListener {
            val intent = HomeActivity.callHomeActivity(this)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun addObservers() {
        viewModel.scoreData.observe(this, scoreDataObserver)
    }

    override fun removeObservers() {
        viewModel.scoreData.removeObserver(scoreDataObserver)
    }

    override fun onBackPressed() {
        // Navigate back to HomeActivity
        activityLauncher.launch(HomeActivity.callHomeActivity(this))
        finishAffinity()
    }

    /**
     * Setup the Toolbar
     */
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun loadScoreData() {
        // Get intent data from the extras
        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        val totalQuestions = intent.getIntExtra(EXTRA_TOTAL, 10)

        // Pass score data to the view model for calculate the score
        viewModel.processScoreData(score, totalQuestions)
    }

    // Observable variable to listen the updated value and set into the view
    private val scoreDataObserver = Observer<ResultViewModel.ScoreData> { scoreData ->
        "${scoreData.score}/${scoreData.totalQuestions}".also { binding.tvScore.text = it }
        "${scoreData.percentage}%".also { binding.tvPercentage.text = it }
        binding.tvFeedback.text = scoreData.feedback
    }
}