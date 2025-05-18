package com.example.pi3_turma1grupo5.utils

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun buscarCategorias(
    uid: String,
    onSuccess: (List<String>) -> Unit,
){
    Firebase.firestore.collection("usuarios")
        .document(uid)
        .collection("categorias")
        .get()
        .addOnSuccessListener { resultado ->
            val categoriasExistentes = mutableListOf<String>()

            for (documento in resultado.documents){
                categoriasExistentes.add(documento.id)
            }
            onSuccess(categoriasExistentes.distinct()) // remove categorias com o mesmo nome
        }
        .addOnFailureListener { erro ->
            Log.e("Firebase", "Erro ao buscar categorias", erro)
        }
    }