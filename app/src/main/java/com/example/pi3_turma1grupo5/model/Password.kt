package com.example.pi3_turma1grupo5.model

data class PasswordClass( // data class para armazenar todas as informações que serão utilizadas posteriormente
    val title:String?,
    val login:String?,
    val password:String,
    val category:String, // será usado posteriormente para separar as senhas por categoria
    val description:String?
)