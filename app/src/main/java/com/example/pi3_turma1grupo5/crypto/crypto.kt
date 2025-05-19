package com.example.pi3_turma1grupo5.crypto

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

object PasswordCrypto {

    fun encryptPasswordGCM(
        password: String,
        encryptionKey: String = "ProjetoIntegrador3Semestre062025"
    ): Pair<String, String> {
        val keyBytes = encryptionKey.toByteArray(Charsets.UTF_8).copyOf(32)
        val secretKey = SecretKeySpec(keyBytes, "AES")

        val iv = ByteArray(12) // 12 bytes para AES-GCM
        SecureRandom().nextBytes(iv)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val gcmSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec)

        val encryptedBytes = cipher.doFinal(password.toByteArray(Charsets.UTF_8))

        val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
        val ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP)

        return Pair(encryptedBase64, ivBase64)
    }
}