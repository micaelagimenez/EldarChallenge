package com.project.eldarchallenge.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.project.eldarchallenge.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // textWatcher to enable login button
        val watcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val nameNotEmpty: Boolean = binding.etName.text?.isNotEmpty() == true
                val lastNameNotEmpty: Boolean = binding.etLastName.text?.isNotEmpty() == true
                binding.btnLogin.isEnabled = nameNotEmpty && lastNameNotEmpty
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        }

        binding.tfName.editText?.addTextChangedListener(watcher)
        binding.tfLastName.editText?.addTextChangedListener(watcher)

        binding.btnLogin.setOnClickListener {
            // save user data
            val myPrefs: SharedPreferences =
                getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val editor = myPrefs.edit()
            editor.putString("name", binding.etName.text.toString())
            editor.putString("lastName", binding.etLastName.text.toString())
            editor.apply()
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}