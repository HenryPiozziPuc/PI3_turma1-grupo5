package com.example.pi3_turma1grupo5.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.pi3_turma1grupo5.ui.theme.DarkBlue
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.utils.AddCategoryBD
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun AdicionarCategoriaScreen(
    onBack: () -> Unit,
    onCategoriaAdicionada: (String) -> Unit
){
    val auth = Firebase.auth

    var tituloCategoria by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable {onBack()}
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ){
        Card(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .padding(16.dp)
                .imePadding()
                .clickable(enabled = false){},
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.White
            )
        ){
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Nova Categoria ",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = tituloCategoria,
                    onValueChange = { tituloCategoria = it },
                    label = {Text("Nome *")},
                    isError = tituloCategoria.isNullOrEmpty(),
                    supportingText = {
                        if(tituloCategoria.isNullOrEmpty()){
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

                val context = LocalContext.current
                Button(
                    onClick = {
                        AddCategoryBD(
                            tituloCategoria,
                            context = context,
                            onCategoriaAdicionada = {categoriaSalva ->
                                onCategoriaAdicionada(categoriaSalva)
                                onBack()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = tituloCategoria.isNotEmpty()
                ){
                    Text("Adicionar")
                }
            }
        }
    }
}