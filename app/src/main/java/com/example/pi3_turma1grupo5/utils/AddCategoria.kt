package com.example.pi3_turma1grupo5.utils

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

fun AddCategoryBD(
    tituloCategoria: String,
    context: Context,
    onCategoriaAdicionada: (String) -> Unit = {},
    onBack: () -> Unit = {}
){
    val auth = Firebase.auth
    val firestore = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    val uid = user?.uid

    if(!uid.isNullOrEmpty()){

        firestore.collection("usuarios")
            .document(uid)
            .collection("categorias")
            .document(tituloCategoria)
            .set(hashMapOf("criadoEm" to FieldValue.serverTimestamp()))
            .addOnSuccessListener {
                Toast.makeText(context, "Categoria salva com sucesso!", Toast.LENGTH_SHORT).show()

                onCategoriaAdicionada(tituloCategoria)
                onBack()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao salvar categoria!", Toast.LENGTH_SHORT).show()
            }
        } else {
        Toast.makeText(context, "UID inválido/usuário não logado", Toast.LENGTH_SHORT).show()
    }
}