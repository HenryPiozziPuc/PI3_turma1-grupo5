package com.example.pi3_turma1grupo5.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.example.pi3_turma1grupo5.ui.theme.DarkBlue
import com.example.pi3_turma1grupo5.utils.CarregarCategorias
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.Preview
import com.example.pi3_turma1grupo5.crypto.PasswordCrypto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarSenhaScreen(
    onBack: () -> Unit,
    listaCategorias: MutableList<String>,
    senhaAtual: ClasseSenha = ClasseSenha(),
    onSenhaExcluida: (ClasseSenha) -> Unit
) {
    val auth = Firebase.auth
    val user = auth.currentUser
    val uid = user?.uid

    var estadoSenha by remember { mutableStateOf(senhaAtual) }
    var mostrarMenu by remember { mutableStateOf(false) }
    var categoriaSelecionada by remember { mutableStateOf(senhaAtual.categoria ?: "") }

    LaunchedEffect(uid) {
        if (!uid.isNullOrEmpty()) {
            CarregarCategorias(listaCategorias)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable { onBack() }
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .padding(16.dp)
                .clickable(enabled = false) {},
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Editar Senha",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = estadoSenha.titulo ?: "",
                    onValueChange = { estadoSenha = estadoSenha.copy(titulo = it) },
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
                    value = estadoSenha.senha ?: "",
                    onValueChange = { estadoSenha = estadoSenha.copy(senha = it) },
                    label = { Text("Senha *") },
                    isError = estadoSenha.senha.isNullOrEmpty(),
                    supportingText = {
                        if (estadoSenha.senha.isNullOrEmpty()) {
                            Text(
                                text = "Campo obrigatório",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = TextFieldDefaults.colors()
                )

                OutlinedTextField(
                    value = estadoSenha.descricao ?: "",
                    onValueChange = { estadoSenha = estadoSenha.copy(descricao = it) },
                    label = { Text("Descrição") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = TextFieldDefaults.colors(),
                    maxLines = 2
                )

                ExposedDropdownMenuBox(
                    expanded = mostrarMenu,
                    onExpandedChange = { mostrarMenu = !mostrarMenu },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = categoriaSelecionada,
                        onValueChange = {},
                        label = { Text("Categoria *") },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = mostrarMenu)
                        },
                        isError = categoriaSelecionada.isEmpty(),
                        supportingText = {
                            if (categoriaSelecionada.isEmpty()) {
                                Text(
                                    text = "Selecione uma categoria",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = mostrarMenu,
                        onDismissRequest = { mostrarMenu = false }
                    ) {
                        listaCategorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text(categoria) },
                                onClick = {
                                    categoriaSelecionada = categoria
                                    mostrarMenu = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val context = LocalContext.current
                Button(
                    onClick = {
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = categoriaSelecionada.isNotEmpty() && !estadoSenha.senha.isNullOrEmpty()
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}





