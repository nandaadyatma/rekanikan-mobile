package com.example.rekanikan.view.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.example.rekanikan.R
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.data.result.Result
import com.example.rekanikan.databinding.ActivityRegisterBinding
import com.example.rekanikan.view.custom.CustomEmailEditText
import com.example.rekanikan.view.custom.CustomPasswordEditText
import com.example.rekanikan.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: CustomEmailEditText
    private lateinit var passwordEditText: CustomPasswordEditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        nameEditText = binding.nameEditText
        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        registerButton = binding.registerButton

        //validasi nama
        nameEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (nameEditText.text.isEmpty()){
                    nameEditText.error = getString(R.string.name_cannot_empty)
                } else {
                    nameEditText.error = null
                }
                setMyButtonEnable()
            }

        }


        )

        // email
        emailEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        )

        // password
        passwordEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })



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
        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.register(name, email, password).observe(this) { result ->
                when (result){
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        alertDialog(email)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()

                    }
                }

            }


        }


        binding.loginShortcut.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setMyButtonEnable(){
        val password = passwordEditText.text
        val email = emailEditText.text.toString()
        val validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        registerButton.isEnabled = (password != null && password.toString().length >= 8 && email != null && validEmail)
        if (registerButton.isEnabled){
            binding.registerButton.alpha = 1f
        } else {
            binding.registerButton.alpha = 0.3f
        }
    }

    private fun alertDialog(email: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            val message = getString(R.string.register_success, email)
            val ok = getString(R.string.next)
            setMessage(message)
            setPositiveButton(ok) { _, _ ->
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