package com.example.pi3_turma1grupo5.ui.components

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults.ContainerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pi3_turma1grupo5.model.PasswordClass
import com.example.pi3_turma1grupo5.ui.theme.BackgroundLight
import com.example.pi3_turma1grupo5.ui.theme.Blue
import com.example.pi3_turma1grupo5.ui.theme.DarkBlue
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import com.example.pi3_turma1grupo5.utils.AddPassowordBD


@Composable
fun AdicionarSenhaScreen(
    onBack: () -> Unit,
    objSenha: PasswordClass = PasswordClass()
) {
    var estadoSenha by remember { mutableStateOf(objSenha) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Blue
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Adicionar senha",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = estadoSenha.title ?: "",
                onValueChange = { novoTitulo ->
                    estadoSenha = estadoSenha.copy(title = novoTitulo)
                    },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(),
                    shape = RoundedCornerShape(8.dp)
                )


            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = estadoSenha.login ?: "",
                onValueChange = { novoLogin ->
                    estadoSenha = estadoSenha.copy(login = novoLogin)
                    },
                    label = { Text("Login") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(),
                    shape = RoundedCornerShape(8.dp)
                )


                Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = estadoSenha.password ?: "",
                onValueChange = { novaPassword ->
                    estadoSenha = estadoSenha.copy(password = novaPassword)
                    },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(),
                    shape = RoundedCornerShape(8.dp)
                )


            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = estadoSenha.description ?: "",
                onValueChange = { novaDescription ->
                    estadoSenha = estadoSenha.copy(description = novaDescription)
                    },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(),
                    shape = RoundedCornerShape(8.dp)
            )


            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    AddPassowordBD(
                        password = estadoSenha,
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                text = "Adicionar",
                style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}



/*@Preview(showBackground = true)
@Composable
fun AddSenhaPreview(){
    PI3_turma1grupo5Theme{
        AdicionarSenhaScreen(onAddSenhaClick = {_ ->}
        )
    }
}*/
