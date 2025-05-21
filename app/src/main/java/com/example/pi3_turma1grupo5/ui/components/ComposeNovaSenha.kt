package com.example.pi3_turma1grupo5.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.example.pi3_turma1grupo5.ui.theme.Blue
import com.example.pi3_turma1grupo5.ui.theme.DarkBlue
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.utils.AddPasswordBD
import com.example.pi3_turma1grupo5.utils.buscarCategorias
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AdicionarSenhaScreen(
        onBack: () -> Unit,
        objSenha: ClasseSenha = ClasseSenha(),
        onSenhaAdicionada: (ClasseSenha) -> Unit
    ) {
        val auth = Firebase.auth
        val user = auth.currentUser
        val uid = user?.uid

        var estadoSenha by remember { mutableStateOf(objSenha) }
        var mostrarMenu by remember {mutableStateOf(false)} // controla a visibilidade do menu suspenso
        var categoriaSelecionada by remember { mutableStateOf("") }
        val listacategorias = remember { mutableStateListOf<String>()}

        // busca as categorias quando a tela é aberta
        // LahchedEffect: é executado quando o componente inicia
        LaunchedEffect(uid) {
            if (!uid.isNullOrEmpty()) {
                buscarCategorias(
                    uid = uid,
                    onSuccess = { categorias ->
                        listacategorias.clear()
                        listacategorias.addAll(categorias)
                    },
                )
            }
        }

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
                    .padding(16.dp)
                    .clickable(enabled = false){},
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Blue,
                    contentColor = Color.White
                )
            ) {
                Column(

                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()) // muda o "Column" para um container que permite rolagem vertical
                        .imePadding(), // adiciona um espaço para o teclado não ficar em cima dos campos
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nova Senha",
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
                        label = { Text("Senha") },
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

                    // ExposedDropdownMenuBox: componente que já vem com padrões e comportamentos do material design (padrão usado em formulários)

                    ExposedDropdownMenuBox( // container principal (gerencia o estado e coordena os componentes filhos)
                        expanded = mostrarMenu,
                        onExpandedChange = { mostrarMenu = !mostrarMenu },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = categoriaSelecionada,
                            onValueChange = {},
                            label = { Text("Categoria") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = mostrarMenu)
                            },
                            colors = TextFieldDefaults.colors(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .menuAnchor() //serve para "juntar" o campo de texto ao menu suspenso -> padronização
                        )

                        ExposedDropdownMenu(
                            expanded = mostrarMenu,
                            onDismissRequest = { mostrarMenu = false }
                        ) {
                                listacategorias.forEach { categoria ->
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
                            AddPasswordBD(
                                password = estadoSenha.copy(
                                    categoria = categoriaSelecionada // adiciona o campo "categoria" do objeto
                                ),
                                context = context,
                                onSenhaAdicionada = {senhaSalva ->
                                    onSenhaAdicionada(senhaSalva)
                                    onBack()
                                }
                            ) },
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
        AdicionarSenhaScreen(onBack = {},  objSenha = ClasseSenha(), onSenhaAdicionada = {})
    }
}
