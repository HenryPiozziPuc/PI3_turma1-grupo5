package com.example.pi3_turma1grupo5.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.utils.ExcluirCategorias
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MoldeCategoria(
    titulo: String,
    listaSenhas: List<ClasseSenha> = emptyList(), // pede a lista de senhas como parâmetro
    listaCategorias: MutableList<String>
){
    val senhasCategoria = listaSenhas.filter { it.categoria == titulo }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // espaço de cada categoria
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp), // bottom -> espaço entre o texto e o divider
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primaryContainer

            )
            if (titulo != "Sites Web") {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Delete, "Excluir categoria")
                }
            }
        }
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if(senhasCategoria.isEmpty()){
            Text(
                text = "Nenhuma senha cadastrada",
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // parte que exibe as senha da categoria especifica
                listaSenhas.filter { it.categoria == titulo}.forEach { senha ->
                    MoldeSenha(senha) // isso vai ser recomposto ao adicionar uma nova senha a  lista
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        val context = LocalContext.current
        if(showDialog){
            AlertDialog(
                onDismissRequest = {showDialog = false},
                title = {Text(text = "Confirmar exclusão")},
                text = {Text(text = "Tem certeza que deseja excluir a categoria $titulo ?")},
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            ExcluirCategorias(
                                titulo = titulo,
                                listaCategorias = listaCategorias,
                                context = context)}
                    ) {
                        Text("Excluir")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {showDialog = false}
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}