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
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoldeSenhaPreview() {
    PI3_turma1grupo5Theme {
        MoldeSenha(
            password = ClasseSenha(
                titulo = "Gov Br",
                login = "bruno@bruno.com",
                senha = "24#qwe",
                categoria = "Sites Web",
                descricao = "IR"
            )
        )
    }
}
