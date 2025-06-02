package com.example.pi3_turma1grupo5.utils

import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun refreshAll(
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onCategoriesLoaded: (List<String>) -> Unit,
    onPasswordsLoaded: (List<ClasseSenha>) -> Unit
) {
    val uid = Firebase.auth.currentUser?.uid ?: return

    onStart() // Ativa estado de loading

    BuscarCategorias(uid = uid, onSuccess = { categorias ->
        val ordenadas = categorias
            .sortedByDescending { (_, temSenhas) -> temSenhas }
            .map { (nome, _) -> nome }

        onCategoriesLoaded(ordenadas)

        BuscarSenhas(uid = uid, onSuccess = { senhas ->
            onPasswordsLoaded(senhas)
            onComplete() // desativa o loading
        })
    })
}
