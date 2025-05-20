package com.example.pi3_turma1grupo5.ui.screens

import com.example.pi3_turma1grupo5.R
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pi3_turma1grupo5.ui.theme.BackgroundLight
import com.example.pi3_turma1grupo5.ui.theme.LightBlue
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme

class OnBoardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PI3_turma1grupo5Theme {
                val context = this
                OnboardingScreen(
                    onFinish = {
                        val intent = Intent(context, WelcomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}


@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pages = listOf(
        stringResource(R.string.onBoard1),
        stringResource(R.string.onBoard2),
        stringResource(R.string.onBoard3)
    )

    var currentPage by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Box central com texto menor e alinhado ao topo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 400.dp)
                    .weight(0.6f)
                    .background(LightBlue.copy(alpha = 0.7f)) // opcional: leve destaque visual
                    .padding(24.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = pages[currentPage],
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            // Botões
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentPage > 0) {
                    Button(onClick = { currentPage-- }) {
                        Text("Voltar")
                    }
                } else {
                    Spacer(modifier = Modifier.width(96.dp))
                }

                if (currentPage < pages.lastIndex) {
                    Button(onClick = { currentPage++ }) {
                        Text("Próximo")
                    }
                } else {
                    Button(onClick = onFinish) {
                        Text("Começar")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    PI3_turma1grupo5Theme {
        OnboardingScreen(onFinish = {}) // preview ignora a navegação real
    }
}
