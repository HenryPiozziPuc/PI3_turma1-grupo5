package com.example.pi3_turma1grupo5.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.ui.theme.BackgroundLight
import com.example.pi3_turma1grupo5.ui.theme.DarkBlue
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.ui.theme.Typography
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import android.content.Context
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.stringResource
import com.example.pi3_turma1grupo5.R
import com.google.firebase.firestore.FirebaseFirestore

data class SenhaError(var hasError: Boolean, val errorCode: Int)

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PI3_turma1grupo5Theme {
                SignUpScreenPreview()
            }
        }
    }
}

@Composable
fun SignUpScreen() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var masterPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var termsAccepted by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var emailError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var senhaError by remember { mutableStateOf(SenhaError(false, 0)) }
    var termsError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Super ID",
                style = Typography.displayMedium,
                color = DarkBlue
            )

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .border(2.dp, DarkBlue, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = false
                        },
                        label = { Text("Nome") },
                        isError = nameError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (nameError) {
                        Text(
                            text = "Insira seu nome",
                            color = Color.Red,
                            style = Typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = false
                        },
                        label = { Text("Email") },
                        isError = emailError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (emailError) {
                        Text(
                            text = "Email inválido",
                            color = Color.Red,
                            style = Typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = masterPassword,
                        onValueChange = {
                            masterPassword = it
                            senhaError = SenhaError(false, 0)
                        },
                        label = { Text("Senha Mestre") },
                        isError = senhaError.hasError,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                                    tint = DarkBlue
                                )
                            }
                        }
                    )
                    if (senhaError.hasError) {
                        if(senhaError.errorCode == 0) {
                            Text(
                                text = "Senha é obrigatória",
                                color = Color.Red,
                                style = Typography.bodySmall,
                                modifier = Modifier.align(Alignment.Start)
                            )
                        }
                        if(senhaError.errorCode == 1) {
                            Text(
                                text = "Senha precisa ter 6 caracteres",
                                color = Color.Red,
                                style = Typography.bodySmall,
                                modifier = Modifier.align(Alignment.Start)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = termsAccepted,
                            onCheckedChange = {
                                termsAccepted = it
                                termsError = false
                            }
                        )
                        Text(text = "Eu aceito os ")
                        TextButton(onClick = { showTermsDialog = true }) {
                            Text("Termos de uso")
                        }
                    }
                    if (termsError) {
                        Text(
                            text = "Você precisa aceitar os termos de uso",
                            color = Color.Red,
                            style = Typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            nameError = name.trim().isEmpty()
                            emailError = !isEmailValid(email)
                            termsError = !termsAccepted

                            if(!masterPassword.isNotBlank()){
                                senhaError = SenhaError(true, 0)
                            } else if(masterPassword.length < 6) {
                                senhaError = SenhaError(true, 1)
                            }

                            if (!nameError && !emailError && !senhaError.hasError && termsAccepted) {
                                CriarConta(context, name, email, masterPassword) {
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!name.trim().isEmpty() && isEmailValid(email) && masterPassword.length >= 6 && termsAccepted) DarkBlue else Color.LightGray,
                            contentColor = if (!name.trim().isEmpty() && isEmailValid(email) && masterPassword.length >= 6 && termsAccepted) Color.White else Color.Gray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(12.dp),
                                ambientColor = Color.Black.copy(alpha = 0.3f),
                                spotColor = Color.Black.copy(alpha = 0.3f)
                            )
                    ) {
                        Text("Criar conta")
                    }
                }
            }
        }

        if (showTermsDialog) {
            AlertDialog(
                onDismissRequest = { showTermsDialog = false },
                title = { Text("Termos e Condições") },
                text = {
                    Box(
                        modifier = Modifier
                            .heightIn(min = 100.dp, max = 400.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(stringResource(R.string.termos_condicoes))
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showTermsDialog = false }) {
                        Text("Fechar")
                    }
                }
            )
        }
    }
}

fun CriarConta(
    context: Context,
    name: String,
    email: String,
    masterPassword: String,
    onSuccess: () -> Unit
) {
    val auth = Firebase.auth
    val firestore = FirebaseFirestore.getInstance()

    val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    auth.createUserWithEmailAndPassword(email, masterPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val uid = user?.uid

                if (uid != null) {
                    val userDoc = hashMapOf(
                        "uid" to uid,
                        "deviceId" to deviceId,
                        "nome" to name,
                        "email" to email
                    )

                    firestore.collection("usuarios").document(uid)
                        .set(userDoc)
                        .addOnSuccessListener {
                            firestore.collection("usuarios").document(uid)
                                .collection("senhas")
                                .document("placeholder")
                                .set(hashMapOf("placeholder" to true))
                            user.sendEmailVerification()
                                .addOnCompleteListener { emailTask ->
                                    if (emailTask.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            "Conta criada com sucesso! Verifique seu email para ativar a conta.",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        onSuccess()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Conta criada, mas falha ao enviar email de verificação.",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        onSuccess()
                                    }
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Erro ao salvar dados no Firestore", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                val exceptionMessage = task.exception?.message ?: ""
                if (exceptionMessage.contains("email")) {
                    Toast.makeText(
                        context,
                        "Este email já está cadastrado. Faça login ou use outro email.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(context, "Erro ao criar conta: $exceptionMessage", Toast.LENGTH_SHORT).show()
                    Log.e("CriarConta", "Erro", task.exception)
                }
            }
        }
}

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    PI3_turma1grupo5Theme {
        SignUpScreen()
    }
}