package com.example.pi3_turma1grupo5.utils

import android.util.Log
import com.example.pi3_turma1grupo5.model.PasswordClass
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

fun AddPassowordBD(
    password: PasswordClass
){
    val auth = Firebase.auth
    val firestore = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    val uid = user?.uid

    if (!uid.isNullOrEmpty()) {
        val objSenha = hashMapOf(
            "title" to password.title,
            "login" to password.login,
            "password" to password, // FAZER A CRIPTOGRAFIA ANTES DO HASHMAP!!!
            "category" to password.category, // VERIFICAR QUE ELE SÓ PODE MANDAR CATEGORIAS JÁ EXISTENTES
            "description" to password.description
        )

        firestore.collection("usuarios")
            .document(uid)
            .collection("senhas")
            .document()
            .set(objSenha)
            .addOnSuccessListener {
                Log.d("Firestore", "Senha salva com sucesso!")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao salvar senha", e)
            }
        } else {
            Log.e("Auth","UID inválido ou usuário não logado ")
    }
}