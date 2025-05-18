package com.example.pi3_turma1grupo5.utils

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun buscarCategorias(
    userId: String, callback: (List<String>) -> Unit){
    Firebase.firestore.collection("categorias")
        .whereEqualTo("uid", userId)
        .get()
        .addOnSuccessListener { resultado ->
            val categoriasExistentes = mutableListOf<String>()

            for (documento in resultado.documents){
                val nomeCategoria = documento.id
                categoriasExistentes.add(nomeCategoria)
            }
            callback(categoriasExistentes.distinct()) // remove categorias com o mesmo nome
        }
        .addOnFailureListener { erro ->
            Log.e("Firebase", "Erro ao buscar categorias", erro)
            callback(emptyList())
        }
    }