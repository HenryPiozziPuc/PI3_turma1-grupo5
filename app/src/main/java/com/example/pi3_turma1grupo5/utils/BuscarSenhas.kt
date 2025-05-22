package com.example.pi3_turma1grupo5.utils

import android.util.Log
import com.example.pi3_turma1grupo5.model.ClasseSenha
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

fun BuscarSenhas(
    uid: String,
    onSuccess: (List<ClasseSenha>) -> Unit,
    onFailure: (Exception) -> Unit = {}
) {
    Firebase.firestore.collection("usuarios")
        .document(uid)
        .collection("categorias")
        .get()
        .addOnSuccessListener { CategoriaResultado ->
            val listaSenhas = mutableListOf<ClasseSenha>()

            for (documentoCategoria in CategoriaResultado.documents) {
                val senhasBruto = documentoCategoria.get("senhas") as? List<Map<String, Any>> ?: emptyList()

                senhasBruto.forEach { mapSenha ->
                    val encryptedPassword = mapSenha["password"] as? String ?: ""
                    val iv = mapSenha["iv"] as? String ?: ""

                    val decryptedPassword = if (encryptedPassword.isNotEmpty() && iv.isNotEmpty()) {
                        try {
                            decryptPasswordGCM(encryptedPassword, iv)
                        } catch (e: Exception) {
                            Log.e("BuscarSenhas", "Erro na descriptografia: ${e.message}")
                            encryptedPassword
                        }
                    } else {
                        encryptedPassword
                    }

                    listaSenhas.add(
                        ClasseSenha(
                            titulo = mapSenha["title"] as? String ?: "",
                            login = mapSenha["login"] as? String ?: "",
                            senha = decryptedPassword,
                            descricao = mapSenha["description"] as? String ?: "",
                            categoria = mapSenha["category"] as? String ?: ""
                        )
                    )
                }
            }

            onSuccess(listaSenhas)
        }
        .addOnFailureListener { erro ->
            onFailure(erro)
        }
}

fun decryptPasswordGCM(
    encryptedPasswordBase64: String,
    ivBase64: String,
    encryptionKey: String = "ProjetoIntegrador3Semestre062025"
): String {
    return try {
        val keyBytes = encryptionKey.toByteArray(Charsets.UTF_8).copyOf(32)
        val secretKey = SecretKeySpec(keyBytes, "AES")

        val iv = Base64.decode(ivBase64, Base64.NO_WRAP)
        val encryptedBytes = Base64.decode(encryptedPasswordBase64, Base64.NO_WRAP)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val gcmSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        String(decryptedBytes, Charsets.UTF_8)
    } catch (e: Exception) {
        Log.e("decryptPasswordGCM", "Erro na descriptografia", e)
        "" // ou retorne encryptedPasswordBase64 se preferir manter a criptografia visível
    }
}
