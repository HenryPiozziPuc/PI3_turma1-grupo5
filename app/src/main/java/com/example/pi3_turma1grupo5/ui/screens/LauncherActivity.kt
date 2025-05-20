package com.example.pi3_turma1grupo5.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // Usuário está logado -> vá para MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Usuário não está logado -> vá para OnBoardActivity
            startActivity(Intent(this, OnBoardActivity::class.java))
        }

        finish() // Finaliza a LauncherActivity
    }
}
