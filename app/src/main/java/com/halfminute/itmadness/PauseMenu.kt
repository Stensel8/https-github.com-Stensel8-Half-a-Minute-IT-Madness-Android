package com.halfminute.itmadness

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class PauseMenu : AppCompatActivity() {
    private lateinit var sharedPref: SharedPref
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this)
        setTheme(if (sharedPref.loadNightMode()) R.style.darkTheme else R.style.lightTheme)
        sharedPreferences = getSharedPreferences("actualGame", MODE_PRIVATE)
        sharedPref.loadLocale(this)
        setContentView(R.layout.pause_menu)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                resumeCurrentGame()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun resumeCurrentGame() {
        val intent = when (sharedPreferences.getString("actualGame", "")) {
            "math" -> Intent(this, MathGame::class.java)
            "guessing" -> Intent(this, GuessingGame::class.java)
            // Add cases for different LanguageGame modes if needed
            else -> Intent(this, MainActivity::class.java) // Default to Main if unknown
        }
        startActivity(intent)
        finish()
    }

    fun resumeGame(view: View?) {
        Settings.btnAnimation(view)
        resumeCurrentGame()
    }

    fun goSettings(view: View?) {
        Settings.btnAnimation(view)
        val sharedPreferences = getSharedPreferences("activity", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("activity", "pause")
        editor.apply()
        val intent = Intent(this@PauseMenu, Settings::class.java)
        startActivity(intent)
        finish()
    }

    fun goMain(view: View?) {
        Settings.btnAnimation(view)
        val intent = Intent(this@PauseMenu, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun changeGame(view: View?) {
        Settings.btnAnimation(view)
        val intent = Intent(this@PauseMenu, ChooseGame::class.java)
        startActivity(intent)
        finish()
    }
}
