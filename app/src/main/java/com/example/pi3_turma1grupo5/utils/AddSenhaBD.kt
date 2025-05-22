package com.example.pi3_turma1grupo5.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.example.pi3_turma1grupo5.crypto.PasswordCrypto
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

fun AddPasswordBD(
    password: ClasseSenha,
    context: Context,
    onSenhaAdicionada: (ClasseSenha) -> Unit = {},
    onBack: () -> Unit = {}
){
    val auth = Firebase.auth
    val firestore = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    val uid = user?.uid

    if (!uid.isNullOrEmpty()) {

        val (senhaCriptografada, ivBase64) = PasswordCrypto.encryptPasswordGCM(password.senha)

        val objSenha = hashMapOf(
            "title" to password.titulo,
            "login" to password.login,
            "password" to senhaCriptografada,
            "iv" to ivBase64,
            "category" to password.categoria,
            "description" to password.descricao
        )

        firestore.collection("usuarios")
            .document(uid)
            .collection("categorias")
            .document(password.categoria)
            .update("senhas", FieldValue.arrayUnion(objSenha))
            .addOnSuccessListener {
                Toast.makeText(context, "Senha salva com sucesso!", Toast.LENGTH_SHORT).show()

                onSenhaAdicionada(password)
                onBack()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao salvar senha!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "UID inválido/usuário não logado", Toast.LENGTH_SHORT).show()
    }
}