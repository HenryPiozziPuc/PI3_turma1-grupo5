package com.example.pi3_turma1grupo5.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.example.pi3_turma1grupo5.ui.components.AdicionarCategoriaScreen
import com.example.pi3_turma1grupo5.ui.components.AdicionarSenhaScreen
import com.example.pi3_turma1grupo5.ui.components.MoldeCategoria
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.ui.theme.SoftGray
import com.example.pi3_turma1grupo5.utils.BuscarCategorias
import com.example.pi3_turma1grupo5.utils.BuscarSenhas
import com.example.pi3_turma1grupo5.utils.CarregarCategorias
import com.example.pi3_turma1grupo5.utils.refreshAll
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
                                            logo ele pediu para add OptIn para indicar isso
                                        */
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val auth = Firebase.auth
    val user = auth.currentUser
    val uid = user?.uid

    var mostrarMenu by remember {mutableStateOf(false)} // controlar a visibilidade do menu suspenso
    var mostrarAddSenha by remember {mutableStateOf(false)}
    var mostrarAddCategoria by remember {mutableStateOf(false)}

    val listaSenhas = remember { mutableStateListOf<ClasseSenha>() } // lista definitiva
    val listaCategorias = remember {mutableStateListOf<String>()}

    var isRefreshing by remember {mutableStateOf(false)}


    if(Firebase.auth.currentUser == null){
        Text("Usuário não logado")
        return
    }


    // busca as senhas quando a tela é aberta
    //LahchedEffect: é executado quando o componente inicia
    LaunchedEffect(Unit){
        Firebase.auth.currentUser?.uid?.let {uid ->
            if (!uid.isNullOrEmpty()) {
                BuscarSenhas(
                    uid = uid,
                    onSuccess = { senhas ->
                        listaSenhas.clear()
                        listaSenhas.addAll(senhas)
                    }
                )
            }
        }
    }

    // busca as categorias personalizadas quando a tela é aberta
    LaunchedEffect(Unit) {
        Firebase.auth.currentUser?.uid?.let { uid ->
            if(!uid.isNullOrEmpty()){
                CarregarCategorias(listaCategorias)
            }
        }
    }



    Scaffold(
        containerColor = SoftGray,
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
                                mostrarAddSenha = true // indica que o compose de nova senha foi acionado
                            }
                        )
                        // 2 opção
                        DropdownMenuItem(
                            text = {Text("Adicionar nova categoria")},
                            onClick = {
                                mostrarMenu = false
                                mostrarAddCategoria = true // indica que o compose de nova categoria foi acionado
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
        // SwipeRefresh: reage ao usuario arrastar a tela para baixo (ativação)
        SwipeRefresh( // ao puxar a tela para o refresh, é aqui que a operação se inicia
            state = SwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { refreshAll(
                onStart = { isRefreshing = true }, // mostra o icone de loading
                onComplete = { isRefreshing = false }, // esconde o icone
                onCategoriesLoaded = { categories ->
                    listaCategorias.clear()
                    listaCategorias.addAll(categories) // aqui é recomposta a tela
                },
                onPasswordsLoaded = { passwords ->
                    listaSenhas.clear()
                    listaSenhas.addAll(passwords)
                }
            )
        }
        ){
             Column(
                 modifier = Modifier
                     .padding(innerPadding)
                     .fillMaxSize()
                     .background(SoftGray)
                     .verticalScroll(rememberScrollState()) // permite a rolagem da tela
                     .padding(horizontal = 16.dp),
                 verticalArrangement = Arrangement.spacedBy(16.dp)
             ){
                 Text(
                     modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                     text = "Suas senhas:",
                     style = MaterialTheme.typography.headlineSmall, //small para as seções
                     fontWeight = FontWeight.SemiBold
                 )

                 listaCategorias.forEach { categoria ->
                     MoldeCategoria(
                         titulo = categoria,
                         listaSenhas = listaSenhas,
                         listaCategorias = listaCategorias
                     )
                 }
             }

             // abre o compose da nova senha e cria o callback "onSenhaAdicionada"
             if(mostrarAddSenha) {
                 AdicionarSenhaScreen(
                     onBack = {mostrarAddSenha = false},
                     listaCategorias = listaCategorias,
                     onSenhaAdicionada = { novaSenha ->
                         listaSenhas.add(novaSenha)

                     }
                 )
             }

            if(mostrarAddCategoria){
                 AdicionarCategoriaScreen(
                     onBack = {mostrarAddCategoria = false},
                     onCategoriaAdicionada = {novaCategoria ->
                         listaCategorias.add(novaCategoria)
                         }
                     )
                 }
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