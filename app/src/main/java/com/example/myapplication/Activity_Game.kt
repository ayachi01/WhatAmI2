package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer

class Activity_Game : AppCompatActivity() {

    lateinit var settings2: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var screen: View
    private lateinit var text: TextView
    private lateinit var howtoplay: TextView
    private lateinit var BAR: View
    private lateinit var submitanswer: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)


        this.settings2 = findViewById(R.id.settings2)
        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)
        settings2.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent2 = Intent(this, Activity_Game_to_Settings::class.java)
            startActivity(intent2)
        }
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        updateUI(isDarkModeEnabled)

        screen = findViewById(R.id.screen)
        text = findViewById(R.id.WhatAmI)
        //Lives = findViewById(R.id.Lives)
        //Timer = findViewById(R.id.Timer)
        BAR = findViewById(R.id.BAR)
        submitanswer = findViewById(R.id.submitanswer)

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
        settings2.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        settings2.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
        text.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
        BAR.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_bar))
        submitanswer.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        submitanswer.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
    }

    private fun updateLightModeUI() {
        settings2.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons))
        settings2.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.screen))
        text.setTextColor(ContextCompat.getColor(this, R.color.light_mode_text))
        BAR.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_bar))
        submitanswer.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_submit))
        submitanswer.setTextColor(ContextCompat.getColor(this, R.color.light_mode_submit_text))

    }
}