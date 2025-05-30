package com.example.pi3_turma1grupo5.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.model.ClasseSenha

@Composable
fun MoldeCategoria(
    titulo: String,
    listaSenhas: List<ClasseSenha> = emptyList() // pede a lista de senhas como parâmetro
){
    val senhasCategoria = listaSenhas.filter { it.categoria == titulo }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // espaço de cada categoria
    ) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp), // bottom -> espaço entre o texto e o divider
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primaryContainer

        )
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
    }
}