import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

fun decryptPasswordGCM(encryptedPasswordBase64: String, ivBase64: String, encryptionKey: String = "ProjetoIntegrador3Semestre062025"): String {
    val keyBytes = encryptionKey.toByteArray(Charsets.UTF_8).copyOf(32)
    val secretKey = SecretKeySpec(keyBytes, "AES")

    val iv = Base64.decode(ivBase64, Base64.NO_WRAP)
    val encryptedBytes = Base64.decode(encryptedPasswordBase64, Base64.NO_WRAP)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    val gcmSpec = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)

    val decryptedBytes = cipher.doFinal(encryptedBytes)

    return String(decryptedBytes, Charsets.UTF_8)
}