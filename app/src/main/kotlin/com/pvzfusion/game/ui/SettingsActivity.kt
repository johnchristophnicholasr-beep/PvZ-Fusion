package com.pvzfusion.game.ui

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R

class SettingsActivity : AppCompatActivity() {
    private val prefs by lazy { getSharedPreferences("pvz_fusion_settings", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sound = findViewById<Switch>(R.id.setting_sound)
        val music = findViewById<Switch>(R.id.setting_music)
        val particles = findViewById<Switch>(R.id.setting_particles)
        val reducedMotion = findViewById<Switch>(R.id.setting_reduced_motion)
        val difficulty = findViewById<Spinner>(R.id.setting_difficulty)

        sound.isChecked = prefs.getBoolean("sound", true)
        music.isChecked = prefs.getBoolean("music", true)
        particles.isChecked = prefs.getBoolean("particles", true)
        reducedMotion.isChecked = prefs.getBoolean("reduced_motion", false)
        val values = resources.getStringArray(R.array.difficulty_options)
        difficulty.setSelection(values.indexOf(prefs.getString("difficulty", values.first())).coerceAtLeast(0))

        fun persist() {
            prefs.edit()
                .putBoolean("sound", sound.isChecked)
                .putBoolean("music", music.isChecked)
                .putBoolean("particles", particles.isChecked)
                .putBoolean("reduced_motion", reducedMotion.isChecked)
                .putString("difficulty", difficulty.selectedItem.toString())
                .apply()
        }
        listOf(sound, music, particles, reducedMotion).forEach { it.setOnCheckedChangeListener { _, _ -> persist() } }
        difficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) = persist()
        }
        findViewById<Button>(R.id.btn_reset_settings).setOnClickListener {
            sound.isChecked = true; music.isChecked = true; particles.isChecked = true; reducedMotion.isChecked = false
            difficulty.setSelection(0); persist()
            Toast.makeText(this, "Settings restored", Toast.LENGTH_SHORT).show()
        }
    }
}
