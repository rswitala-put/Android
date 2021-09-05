package com.example.myapplication.ui.main.fragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.ui.main.DBClass
import com.example.myapplication.ui.main.models.GameModel
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.android.synthetic.main.fragment_current.view.*
import kotlinx.coroutines.Runnable
import com.example.myapplication.MainActivity as MainActivity1

class CurrentFragment : Fragment() {

    var count: Int = 0
    var handler: Handler = Handler()
    private var firstBreaks: Boolean = true
    private var currentRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val applicationContext: Context = activity!!.applicationContext
        val db = Room.databaseBuilder(
            applicationContext,
            DBClass.AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val view = inflater.inflate(R.layout.fragment_current, container, false)

        val mainActivity = (activity as MainActivity1)

        view.textFirstPlayerName.text = mainActivity.firstPlayerName
        view.textSecondPlayerName.text = mainActivity.secondPlayerName

        if (currentRunnable == null)
            currentRunnable = startCounter(true)

        firstBreaks = mainActivity.firstPlayerStarts

        if (firstBreaks) {
            view.textViewBreaks.text =
                getString(R.string.player_breaks, mainActivity.firstPlayerName)
        } else {
            view.textViewBreaks.text =
                getString(R.string.player_breaks, mainActivity.secondPlayerName)
        }

        view.buttonFirstPlayerPoint.setOnClickListener {
            increaseScore(view.textViewScoreFirst)
        }

        view.buttonSecondPlayerPoint.setOnClickListener {
            increaseScore(view.textViewScoreSecond)
        }

        view.buttonNextRound.setOnClickListener {
            newRound()
        }

        view.buttonSave.setOnClickListener {
            view.buttonSave.isEnabled = false

            val game = GameModel(
                0,
                view.textViewScore.text.toString(),
                view.textViewTime.text.toString(),
                mainActivity.firstPlayerName,
                mainActivity.secondPlayerName
            )
            db.gameDao().insertAll(game)
        }

        view.buttonReset.setOnClickListener {
            stopCounter(currentRunnable!!)
            mainActivity.removeMainFragments()
        }

        return view
    }

    private fun startCounter(resetCounter: Boolean): Runnable {
        if (resetCounter)
            count = 0

        val runnable: Runnable = object : Runnable {
            override fun run() {
                count++

                textViewTime.text = timeUtil(count)
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable, 1000)

        return runnable
    }

    private fun stopCounter(runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    private fun increaseScore(scoreView: TextView) {

        val mainActivity = (activity as MainActivity1)

        val score: String = scoreView.text as String
        var scoreInt: Int = score.toInt()
        scoreInt++

        firstBreaks = !firstBreaks

        if (firstBreaks) {
            textViewBreaks.text = getString(R.string.player_breaks, mainActivity.firstPlayerName)
        } else {
            textViewBreaks.text = getString(R.string.player_breaks, mainActivity.secondPlayerName)
        }

        if (scoreInt < 10) {
            scoreView.text = scoreInt.toString()
        } else {
            scoreView.text = "10"

            buttonFirstPlayerPoint.isEnabled = false
            buttonSecondPlayerPoint.isEnabled = false

            var roundScore: String = textViewScore.text as String

            if (scoreView == textViewScoreFirst) {
                textPlayerWon.text = getString(R.string.player_won, mainActivity.firstPlayerName)

                roundScore = (roundScore[0].toString()
                    .toInt() + 1).toString() + " - " + roundScore[4].toString()

            } else {
                textPlayerWon.text = getString(R.string.player_won, mainActivity.secondPlayerName)

                roundScore = roundScore[0].toString() + " - " + (roundScore[4].toString()
                    .toInt() + 1).toString()
            }

            textViewScore.text = roundScore

            textPlayerWon.isVisible = true
            buttonNextRound.isVisible = true

            buttonSave.isEnabled = true

            stopCounter(currentRunnable!!)
        }
    }

    private fun timeUtil(seconds: Int): String {
        var secondsString = ""

        if (seconds % 60 < 10)
            secondsString = "0"

        secondsString += (seconds % 60).toString()

        return (seconds / 60).toString() + ":" + secondsString
    }

    private fun newRound() {
        buttonFirstPlayerPoint.isEnabled = true
        buttonSecondPlayerPoint.isEnabled = true

        textViewScoreFirst.text = "0"
        textViewScoreSecond.text = "0"

        buttonNextRound.isVisible = false
        textPlayerWon.isVisible = false

        buttonSave.isEnabled = false

        currentRunnable = startCounter(false)
    }
}

