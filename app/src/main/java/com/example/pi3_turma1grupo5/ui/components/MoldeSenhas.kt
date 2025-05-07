package com.example.pi3_turma1grupo5.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.model.PasswordClass
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme

@Composable
fun MoldeSenha(
    password: PasswordClass, // recebe o tipo passwordClass como parâmetro
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            if(!password.title.isNullOrBlank()) { // exibe apenas se a string não for nula, vazia ou espaços em branco
                Text(
                    text = password.title, // Campo do Título
                    style = MaterialTheme.typography.titleLarge
                )
            }

            if(!password.login.isNullOrBlank()){
                Text(
                    text = password.login,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Text(
                text = password.password,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            if(!password.description.isNullOrBlank()){
                Text(
                    text = password.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                ) }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MoldeSenhaPreview() {
    PI3_turma1grupo5Theme {
        MoldeSenha(
            password = PasswordClass(
                title = "Gov Br",
                login = "bruno@bruno.com",
                password = "24#qwe",
                category = "Sites Web",
                description = "IR"
            )
        )
    }
}
