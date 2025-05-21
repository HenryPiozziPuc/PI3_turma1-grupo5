package com.example.pi3_turma1grupo5.utils

import android.util.Log
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun BuscarSenhas(
    uid: String,
    onSuccess: (List<ClasseSenha>) -> Unit, // retornar a lista com o tipo do objeto "ClasseSenha" para conter todos os campos
    onFailure: (Exception) -> Unit = {}
)   {
    Firebase.firestore.collection("usuarios")
        .document(uid)
        .collection("categorias")
        .get()
        .addOnSuccessListener { CategoriaResultado ->
            var listaSenhas = mutableListOf<ClasseSenha>() // lista temporária apenas para conversão

            for(documentoCategoria in CategoriaResultado.documents){

                // as?: retorna null se falhar(cast) / Map<String, Any -> representa um objeto genérico do firestore (chave-valor) / ?: emptyList() -> se o cast falhar retorna lista vazia
                val senhasBruto = documentoCategoria.get("senhas") as? List<Map<String, Any>> ?: emptyList()

                senhasBruto.forEach { mapSenha ->
                    listaSenhas.add(
                        ClasseSenha(
                            titulo = mapSenha["title"] as? String ?: "",
                            login = mapSenha["login"] as? String ?: "",
                            senha = mapSenha["password"] as? String ?: "",
                            descricao = mapSenha["description"] as? String ?: "",
                            categoria = mapSenha["category"] as? String ?: ""
                        )
                    )
                }
            }
            onSuccess(listaSenhas)
        }
        .addOnFailureListener {erro ->
            onFailure(erro)
        }
}