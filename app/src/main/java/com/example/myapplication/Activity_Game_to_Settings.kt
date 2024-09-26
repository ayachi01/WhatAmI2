package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_Game_to_Settings : AppCompatActivity() {

    private lateinit var backtogame: Button
    private lateinit var quit: Button
    private lateinit var ChangeTheme: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var brownbar: View
    private lateinit var settingstext: TextView
    private lateinit var musictext: TextView
    private lateinit var darkmode: TextView
    private lateinit var screen: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_to_settings)

        this.backtogame = findViewById(R.id.backtogame)
        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)
        backtogame.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent2 = Intent(this, Activity_Game::class.java)
            startActivity(intent2)
        }


        this.quit = findViewById(R.id.quit)
        quit.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent3 = Intent(this, MainActivity::class.java)
            startActivity(intent3)
        }

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val music = findViewById<SeekBar>(R.id.music)
        music.max = maxVolume

        music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    var startPoint = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    var endPoint = seekBar.progress
                }
            }
        })
        music.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        ChangeTheme = findViewById(R.id.ChangeTheme)
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Load the current dark mode state from SharedPreferences
        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)

        // Initialize views
        brownbar = findViewById(R.id.brownbar)
        settingstext = findViewById(R.id.settingstext)
        musictext = findViewById(R.id.musictext)
        darkmode = findViewById(R.id.darkmode)
        screen = findViewById(R.id.screen)

        // Set the dark mode button listener
        ChangeTheme.setOnClickListener {
            toggleDarkMode()
        }
        // Update UI elements based on the current dark mode state
        updateDarkMode(isDarkModeEnabled)
    }

    private fun toggleDarkMode() {
        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        val newIsDarkModeEnabled = !isDarkModeEnabled
        editor.putBoolean("is_dark_mode_enabled", newIsDarkModeEnabled)
        editor.apply()
        updateDarkMode(newIsDarkModeEnabled)
    }

    private fun updateDarkMode(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            // Update UI elements for dark mode
            screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
            brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_bar))
            settingstext.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
            musictext.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_mdtext))
            darkmode.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_mdtext))
            backtogame.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
            backtogame.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
            ChangeTheme.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
            ChangeTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
            quit.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
            quit.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))

        } else {
            // Update UI elements for light mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            screen.setBackgroundColor(ContextCompat.getColor(this, R.color.screen))
            brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_bar))
            settingstext.setTextColor(ContextCompat.getColor(this, R.color.light_mode_text))
            musictext.setTextColor(ContextCompat.getColor(this, R.color.light_mode_mdtext))
            darkmode.setTextColor(ContextCompat.getColor(this, R.color.light_mode_mdtext))
            backtogame.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons))
            backtogame.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
            ChangeTheme.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
            ChangeTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons))
            quit.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons))
            quit.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
        }
    }
}

// Update UI elements