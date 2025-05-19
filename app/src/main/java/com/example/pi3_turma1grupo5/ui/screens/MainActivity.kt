package com.example.pi3_turma1grupo5.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PI3_turma1grupo5Theme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)/*
                                            Existem APIs aqui que ainda são experimentais do material design 3,
                                            logo precisa add o OptIn para indicar isso
                                        */
@Composable
fun MainScreen() {
    var mostrarMenu by remember {mutableStateOf(false)} // controlar a visibilidade do menu suspenso
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Super ID", style = MaterialTheme.typography.headlineMedium)}, // medium para o título principal
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                actions = {
                    IconButton(onClick = { mostrarMenu = true }) {
                        Icon(Icons.Default.Add, "Adicionar")
                    }
                    //Menu suspenso "+"
                    DropdownMenu(
                        expanded = mostrarMenu,
                        onDismissRequest = {mostrarMenu = false}// se o usuário clicar fora o menu fecha
                    ) {
                        // 1 opção
                        DropdownMenuItem(
                            text = { Text("Adicionar nova senha")},
                            onClick = {
                                mostrarMenu = false// fecha o menu depois de escolher a opção
                            }
                        )
                        // 2 opção
                        DropdownMenuItem(
                            text = {Text("Adicionar nova categoria")},
                            onClick = {
                                mostrarMenu = false
                            }
                        )
                    }
                    IconButton(onClick = { /* lógica de busca */ }) {
                        Icon(Icons.Default.Search, "Procurar senha")
                    }
                    IconButton(onClick = { /* configurações */ }) {
                        Icon(Icons.Default.Settings, "Configurações")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, QRCodeScannerActivity::class.java)
                    context.startActivity(intent)
                },
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 60.dp)
            ){
                Icon(Icons.Default.QrCode, "QR Code")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Senhas:",
                style = MaterialTheme.typography.headlineSmall //small para as seções
            )

            MoldeCategoria("Sites Web")

            MoldeCategoria("Aplicativos")

            MoldeCategoria("Teclados de acesso físico")

        }
    }
}

@Composable
fun MoldeCategoria(titulo: String){
    Column(
        modifier = Modifier.padding(vertical = 8.dp) // espaço de cada categoria
    ) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp), // bottom -> espaço entre o texto e o divider
            text = titulo,
            style = MaterialTheme.typography.titleMedium
        )
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 2.dp)
    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PI3_turma1grupo5Theme {
        MainScreen()
    }
}