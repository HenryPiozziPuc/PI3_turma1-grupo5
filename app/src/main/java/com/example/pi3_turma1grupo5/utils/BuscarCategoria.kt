package com.example.pi3_turma1grupo5.utils

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun BuscarCategorias(
    uid: String,
    onSuccess: (List<Pair<String, Boolean>>) -> Unit,
){
    Firebase.firestore.collection("usuarios")
        .document(uid)
        .collection("categorias")
        .get()
        .addOnSuccessListener { resultado ->
            val categoriasExistentes = mutableListOf<Pair<String, Boolean>>()

            for (documento in resultado.documents){
                val nomeCategoria = documento.id
                val existeSenhas = documento.data?.containsKey("senhas") == true  // ve se o array senhas existe
                        &&(documento.get("senhas") as? List<*>)?.isNotEmpty() == true // ve se o array não esta vazio

                categoriasExistentes.add(Pair(nomeCategoria, existeSenhas)) // add o par para a lista
            }
            onSuccess(categoriasExistentes.distinct()) // remove categorias com o mesmo nome
        }
        .addOnFailureListener { erro ->
            Log.e("Firebase", "Erro ao buscar categorias", erro)
        }
    }