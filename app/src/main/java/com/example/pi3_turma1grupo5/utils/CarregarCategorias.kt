package com.example.pi3_turma1grupo5.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

val auth = Firebase.auth
val user = auth.currentUser
val uid = user?.uid

fun CarregarCategorias(listaCategorias: MutableList<String>){
    Firebase.auth.currentUser?.uid?.let { uid ->
        if(!uid.isNullOrEmpty()){
            BuscarCategorias(
                uid = uid,
                onSuccess = {categorias ->
                    /*
                        "(_,existeSenha)": mostra que só vai usar o segundo valor da lista
                        "-> existeSenhas": mostra que a ordenacao vai usar o que estiver depois da seta como parametro
                    */
                    val categoriasOrdenadas = categorias
                        .sortedByDescending { (_, existeSenhas) -> existeSenhas}
                        .map { (nomeCategoria,_) -> nomeCategoria } // map muda de List<Pair para List<string


                    listaCategorias.clear()
                    listaCategorias.addAll(categoriasOrdenadas)
                }
            )
        }
    }
}