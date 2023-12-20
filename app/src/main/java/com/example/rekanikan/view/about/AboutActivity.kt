package com.example.rekanikan.view.about

import android.R.attr.phoneNumber
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rekanikan.R
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.databinding.ActivityAboutBinding
import com.example.rekanikan.view.welcome.WelcomeActivity


class AboutActivity : AppCompatActivity() {
    private val viewModel by viewModels<AboutViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this){ user ->
            if (!user.isLogin) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }


        }

        viewModel.getSession().observe(this){user ->
            binding.userEmailTv.text = user.email
            binding.userNameTv.text = user.name
        }

        setupAction()
    }

    fun setupAction(){
        binding.logout.setOnClickListener{
            showDialog()
        }
        binding.languageMenu.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.chatCenterMenu.setOnClickListener {
            openWhatsApp()
        }

    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val title = getString(R.string.logout)
        val confirmation = getString(R.string.logout_confirmation)
        val yes = getString(R.string.yes)
        val no = getString(R.string.no)

        builder.setTitle(title)
        builder.setMessage(confirmation)

        builder.setPositiveButton(yes) { dialogInterface: DialogInterface, i: Int ->
            viewModel.logout()
            finish()
        }

        builder.setNegativeButton(no) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun openWhatsApp() {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/+6285171187212")

        startActivity(intent)
    }
}