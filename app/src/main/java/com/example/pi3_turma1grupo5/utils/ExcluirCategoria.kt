package com.example.pi3_turma1grupo5.utils
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun ExcluirCategorias(
    titulo: String,
    listaCategorias: MutableList<String>,
    context: Context,
){
    val auth = com.google.firebase.Firebase.auth
    val firestore = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    val uid = user?.uid

    if(!uid.isNullOrEmpty()) {
        Firebase.firestore.collection("usuarios")
            .document(uid)
            .collection("categorias")
            .document(titulo)
            .delete()
            .addOnSuccessListener { Toast.makeText(context,"Categoria excluída com sucesso!",Toast.LENGTH_SHORT).show()
                CarregarCategorias(listaCategorias)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context,"Erro ao deletar categoria!", Toast.LENGTH_SHORT).show()
            }
    } else {
        Toast.makeText(context, "UID inválido/usuário não logado", Toast.LENGTH_SHORT).show()
    }
}