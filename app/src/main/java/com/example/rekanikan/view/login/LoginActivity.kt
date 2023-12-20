package com.example.rekanikan.view.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.rekanikan.R
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.data.pref.UserModel
import com.example.rekanikan.data.remote.request.LoginRequest
import com.example.rekanikan.data.result.Result
import com.example.rekanikan.databinding.ActivityLoginBinding
import com.example.rekanikan.view.custom.CustomEmailEditText
import com.example.rekanikan.view.custom.CustomPasswordEditText
import com.example.rekanikan.view.main.MainActivity
import com.example.rekanikan.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    private lateinit var passwordEditText: CustomPasswordEditText
    private lateinit var emailEditText: CustomEmailEditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailEditText = binding.edLoginEmail
        loginButton = binding.loginButton
        passwordEditText = binding.edLoginPassword

        //validasi email
        emailEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {
                //do nothing
            }

        })

        //validasi password
        setMyButtonEnable()

        passwordEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {
                //do nothing
            }

        })

        setupView()
        setupAction()
    }

    private fun setMyButtonEnable() {
        val password = passwordEditText.text
        val email = emailEditText.text
        val validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()

        loginButton.isEnabled = password != null && password.toString().length >= 8 && email != null && validEmail
        if (!loginButton.isEnabled){
            binding.loginButton.alpha = 0.3f
        } else {
            binding.loginButton.alpha = 1f
        }
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

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            viewModel.login(email, password).observe(this){ result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            viewModel.saveSession(UserModel(name = result.data.loginResult.name, email = result.data.loginResult.email, token = result.data.loginResult.token))


                            
                            alertDialog()
                        }
                        
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this@LoginActivity, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }


        }

        binding.registerShortcut.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun alertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            val message = getString(R.string.login_success)
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.loadingLoop.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}

