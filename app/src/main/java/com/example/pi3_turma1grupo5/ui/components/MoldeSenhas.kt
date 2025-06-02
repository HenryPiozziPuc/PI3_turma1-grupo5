package com.example.pi3_turma1grupo5.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.ui.theme.PasswordGray

@Composable
fun MoldeSenha(
    password: ClasseSenha, // recebe o tipo passwordClass como parâmetro
    listaCategorias: MutableList<String>
) {
    var mostrarEditarSenha by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { mostrarEditarSenha = true}
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = PasswordGray,
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            if(!password.titulo.isNullOrEmpty()) {
                Text(
                    text = password.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            if(!password.login.isNullOrEmpty()) {
                Text(
                    text = "Usuário: ${password.login}",
                )
            }

            Text(
                text = "Senha: ${password.senha}",
            )

            if(!password.descricao.isNullOrEmpty()) {
                Text(
                    text = password.descricao,
                    style = MaterialTheme.typography.bodySmall, // textos pequenos
                )
            }

            Text(
                text = "Categoria: ${password.categoria}",
                style = MaterialTheme.typography.labelSmall, // textos pequenos/ etiquetas
                color = MaterialTheme.colorScheme.outline
            )

            if(mostrarEditarSenha){
                EditarSenhaScreen(
                    onBack = {mostrarEditarSenha = false},
                    listaCategorias = listaCategorias,
                    senhaAtual = password,
                    onSenhaExcluida = {senhaExcluida ->}
                )
            }
        }
    }
}
