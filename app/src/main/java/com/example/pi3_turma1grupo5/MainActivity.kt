package com.example.pi3_turma1grupo5

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
    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Super ID", style = MaterialTheme.typography.headlineMedium)}, // medium para o título principal
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                actions = {
                    IconButton(onClick = { /* lógica do botão de adicionar senha */ }) {
                        Icon(Icons.Default.Add, "Adicionar nova senha")
                    }
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(Icons.Default.Search, "Procurar senha")
                    }
                    IconButton(onClick = { /* Settings action */ }) {
                        Icon(Icons.Default.Settings, "Configurações")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* QR Code */ },
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

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Sites Web",
                style = MaterialTheme.typography.titleMedium, // titleMedium é para subseções
                color = MaterialTheme.colorScheme.primary
            )
            Divider()

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Aplicativos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Divider()

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Teclados de acesso físico",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PI3_turma1grupo5Theme {
        MainScreen()
    }
}