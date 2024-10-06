package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.SoundPool
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.util.Timer

class Activity_Game : AppCompatActivity() {

    lateinit var settings2: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var screen: View
    private lateinit var brownbar: View
    private lateinit var submitanswer: Button
    private lateinit var ResetButton: ImageButton
    private lateinit var tv_question: TextView
    private lateinit var tv_optionOne: TextView
    private lateinit var tv_optionTwo: TextView
    private lateinit var tv_optionThree: TextView
    private lateinit var ScoreOutput: TextView
    private lateinit var Score: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)

        this.settings2 = findViewById(R.id.settings2)

        settings2.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent2 = Intent(this, Activity_Game_to_Settings::class.java)
            startActivity(intent2)
        }

        this.ResetButton = findViewById(R.id.ResetButton)
        ResetButton.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            restartQuiz()
        }
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        updateUI(isDarkModeEnabled)

        screen = findViewById(R.id.screen)
        brownbar = findViewById(R.id.brownbar)
        submitanswer = findViewById(R.id.submitanswer)
        ScoreOutput = findViewById(R.id.ScoreOutput)
        Score = findViewById(R.id.Score)

        if (isDarkModeEnabled) {
            updateDarkModeUI()
        } else {
            updateLightModeUI()
        }
    }

    private fun updateUI(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun updateDarkModeUI() {

        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
        brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_bar))
        submitanswer.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        submitanswer.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
        Score.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
        ScoreOutput.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
        settings2.setImageResource(R.drawable.dark_mode_settingsbutton)
        ResetButton.setImageResource(R.drawable.dark_mode_resetbutton)

    }

    private fun updateLightModeUI() {

        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.screen))
        brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_bar))
        submitanswer.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_submit))
        submitanswer.setTextColor(ContextCompat.getColor(this, R.color.light_mode_submit_text))
        Score.setTextColor(ContextCompat.getColor(this, R.color.light_mode_score))
        ScoreOutput.setTextColor(ContextCompat.getColor(this, R.color.light_mode_score))
        settings2.setImageResource(R.drawable.settingsbutton)
        ResetButton.setImageResource(R.drawable.resetbutton)


    }

    private fun restartQuiz() {
        var mCurrentPosition = 1
        var mSelectedOptionPosition = 0
        submitanswer.text = "Submit"

    }
}