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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.ui.theme.BackgroundLight
import com.example.pi3_turma1grupo5.ui.theme.DarkBlue
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.ui.theme.Typography
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.firestore

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
    var termsAccepted by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // Estados para erros
    var emailError by remember { mutableStateOf(false) }
    var senhaError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = BackgroundLight
                )
            )
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

                    // Campo Nome
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo Email com erro
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

                    // Campo Senha Mestre com erro
                    OutlinedTextField(
                        value = masterPassword,
                        onValueChange = {
                            masterPassword = it
                            senhaError = false
                        },
                        label = { Text("Senha Mestre") },
                        isError = senhaError,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (senhaError) {
                        Text(
                            text = "Senha é obrigatória",
                            color = Color.Red,
                            style = Typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Checkbox termos
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = termsAccepted,
                            onCheckedChange = { termsAccepted = it }
                        )
                        Text(text = "Eu aceito os ")
                        TextButton(onClick = { showTermsDialog = true }) {
                            Text("termos e condições")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botão criar conta
                    Button(
                        onClick = {
                            emailError = !isEmailValid(email)
                            senhaError = masterPassword.isBlank()

                            if (!emailError && !senhaError && termsAccepted) {
                                CriarConta(context, name, email, masterPassword)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBlue,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(12.dp),
                                ambientColor = Color.Black.copy(alpha = 0.3f),
                                spotColor = Color.Black.copy(alpha = 0.3f)
                            ),
                        enabled = termsAccepted
                    ) {
                        Text("Criar conta")
                    }
                }
            }
        }

        // Diálogo dos termos
        if (showTermsDialog) {
            AlertDialog(
                onDismissRequest = { showTermsDialog = false },
                title = { Text("Termos e Condições") },
                text = {
                    Text("Colocar termos e condições depois")
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
    onSuccess: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
) {
    val auth = Firebase.auth
    val db = Firebase.firestore

    auth.createUserWithEmailAndPassword(email, masterPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                // Comentado temporariamente conforme solicitado
                // val imei = getImei(context)

                val userData = hashMapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email,
                    // "imei" to imei
                )

                db.collection("usuarios").document(uid)
                    .set(userData)
                    .addOnSuccessListener {
                        // Cria uma subcoleção de senhas (inicialmente vazia)
                        val emptySenhaList = hashMapOf<String, Any>() // Pode ser ajustado conforme estrutura
                        db.collection("usuarios").document(uid)
                            .collection("senhas")
                            .document("exemplo")
                            .set(emptySenhaList)
                            .addOnSuccessListener { onSuccess() }
                            .addOnFailureListener { onFailure(it) }
                    }
                    .addOnFailureListener { onFailure(it) }
            } else {
                task.exception?.let { onFailure(it) }
            }
        }
}


/*
@RequiresPermission("android.permission.READ_PRIVILEGED_PHONE_STATE")
fun getImei(context: Context): String {
    val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    return if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        "PERMISSAO_NECESSARIA"
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.imei ?: "IMEI_NAO_DISPONIVEL"
        } else {
            telephonyManager.deviceId ?: "IMEI_NAO_DISPONIVEL"
        }
    }
}*/

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