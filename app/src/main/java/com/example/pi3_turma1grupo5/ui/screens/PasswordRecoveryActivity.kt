package com.example.pi3_turma1grupo5.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.pi3_turma1grupo5.ui.theme.BackgroundLight
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordRecoveryActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PI3_turma1grupo5Theme {
                PasswordRecovryPreview()
            }
        }
    }

}




@Composable
fun PasswordRecovry() {
    var email by remember { mutableStateOf("") }
    var context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundLight
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
                        text = "Recuperação de senha",
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Email", fontStyle = FontStyle.Italic) },
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Button(
                        onClick = {
                            if (email.isNotBlank()) {
                                enviarEmailRecuperacaoSenha(
                                    context,
                                    email = email.trim(),
                                    onSuccess = {
                                        Toast.makeText(context, "Email de recuperação enviado!", Toast.LENGTH_LONG).show()
                                    },
                                    onFailure = {
                                        Toast.makeText(context, "Erro: ${it.message}", Toast.LENGTH_LONG).show()
                                    }
                                )
                            } else {
                                Toast.makeText(context, "Digite um email válido", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B1F56)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .height(55.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = "Enviar email de recuperação",
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

fun enviarEmailRecuperacaoSenha(
    context: Context,
    email: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure(task.exception ?: Exception("Erro desconhecido"))
            }
        }
}



@Preview(showBackground = true)
@Composable
fun PasswordRecovryPreview() {
    PI3_turma1grupo5Theme {
        PasswordRecovry()
    }
}