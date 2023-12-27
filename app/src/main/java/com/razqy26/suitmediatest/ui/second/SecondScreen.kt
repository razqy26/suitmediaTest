package com.razqy26.suitmediatest.ui.second

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.razqy26.suitmediatest.ui.third.ThirdScreen
import com.razqy26.suitmediatest.R
import com.razqy26.suitmediatest.databinding.ActivitySecondScreenBinding

class SecondScreen : AppCompatActivity() {

    private var _binding: ActivitySecondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupAction()

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val nameFromPrefs = sharedPreferences.getString("name", "")
        binding.nameUser.text = nameFromPrefs
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAction() {
        val name = intent.getStringExtra("name")
        binding.nameUser.text = name

        saveNameToSharedPreferences(name)

        val username = intent.getStringExtra("USERNAME")
        binding.username.text = username

        binding.chooseUserButton.setOnClickListener {
            intent = Intent(this, ThirdScreen::class.java)
            startActivity(intent)
        }
    }

    private fun saveNameToSharedPreferences(name: String?) {
        if (!name.isNullOrBlank()) {
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
