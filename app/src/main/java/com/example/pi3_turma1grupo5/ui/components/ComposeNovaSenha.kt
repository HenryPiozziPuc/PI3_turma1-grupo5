package com.example.pi3_turma1grupo5.ui.components

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults.ContainerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.pi3_turma1grupo5.model.PasswordClass
import com.example.pi3_turma1grupo5.ui.theme.BackgroundLight
import com.example.pi3_turma1grupo5.ui.theme.Blue
import com.example.pi3_turma1grupo5.ui.theme.DarkBlue
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.utils.AddPassowordBD


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AdicionarSenhaScreen(
        onBack: () -> Unit,
        objSenha: PasswordClass = PasswordClass()
    ) {
        var estadoSenha by remember { mutableStateOf(objSenha) }
        Box( // fundo que controla os toques fora do formulário
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { onBack() } // fecha formulário ao clicar fora
                .zIndex(1f), // Força a box ficar acima de tudo, principalmente do "topBar"
            contentAlignment = Alignment.Center
        ){
            Card( // área do formulário
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Blue,
                    contentColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nova Senha",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedTextField(
                        value = estadoSenha.title ?: "",
                        onValueChange = { estadoSenha = estadoSenha.copy(title = it) },
                        label = { Text("Título") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = TextFieldDefaults.colors()
                    )
                    OutlinedTextField(
                        value = estadoSenha.login ?: "",
                        onValueChange = { estadoSenha = estadoSenha.copy(login = it) },
                        label = { Text("Login") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = TextFieldDefaults.colors()
                    )
                    OutlinedTextField(
                        value = estadoSenha.password ?: "",
                        onValueChange = { estadoSenha = estadoSenha.copy(password = it) },
                        label = { Text("Senha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = TextFieldDefaults.colors()
                    )
                    OutlinedTextField(
                        value = estadoSenha.description ?: "",
                        onValueChange = { estadoSenha = estadoSenha.copy(description = it) },
                        label = { Text("Descrição") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = TextFieldDefaults.colors(),
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            AddPassowordBD(
                                password = estadoSenha,) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Adicionar")
                    }
                }

            }
        }
    }




@Preview(showBackground = true)
@Composable
fun AddSenhaPreview(){
    PI3_turma1grupo5Theme{
        AdicionarSenhaScreen(onBack = {},  objSenha = PasswordClass())
    }
}
