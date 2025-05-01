package com.example.pi3_turma1grupo5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview // <-- IMPORTANTE pra funcionar o Preview!
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme

class LoginActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PI3_turma1grupo5Theme {
                LoginScreenPreview()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFdbe3f5),
                        Color(0xFF9cb2e5)
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
        ) {
            Text(
                text = "SuperID",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFdbe3f5).copy(alpha = 0.7f)),
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(
                        text = "LOGIN",
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Email", fontStyle = FontStyle.Italic) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        placeholder = { Text("Senha", fontStyle = FontStyle.Italic) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Text(
                        text = "Esqueceu sua senha?",
                        textAlign = TextAlign.End,
                        color = Color(0xFF8888C9),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    Button(
                        onClick = { /* Ação de login */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B1F56)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .height(55.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = "Entrar",
                            fontStyle = FontStyle.Italic,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}