package com.razqy26.suitmediatest.ui.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.razqy26.suitmediatest.databinding.ActivityMainBinding
import com.razqy26.suitmediatest.ui.second.SecondScreen
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.checkButton.setOnClickListener {
            val palindrome = binding.palindromeEditText.text.toString()

            if (palindrome.isNotEmpty()) {
                if (isPalindrome(palindrome)) {
                    Toast.makeText(this, "isPalindrome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "not palindrome", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Palindrome column can't be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.nextButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isNotEmpty()) {
                intent = Intent(this, SecondScreen::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Name column can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPalindrome(str: String): Boolean {
        val cleanStr = str.lowercase(Locale.getDefault()).replace("\\s+".toRegex(), "")

        return cleanStr == cleanStr.reversed()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}