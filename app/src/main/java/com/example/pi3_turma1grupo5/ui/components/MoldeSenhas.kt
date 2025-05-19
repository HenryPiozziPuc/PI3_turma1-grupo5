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
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme

@Composable
fun MoldeSenha(
    password: ClasseSenha, // recebe o tipo passwordClass como parâmetro
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


            Text(
                    text = password.titulo ?: "",
                    style = MaterialTheme.typography.titleLarge
                )



            Text(
                    text = password.login ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )


            Text(
                text = password.senha,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )


            Text(
                    text = password.descricao ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )

            Text(
                text = password.categoria,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
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
