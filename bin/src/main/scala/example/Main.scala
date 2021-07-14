package example;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

object Main {

    val digestName = "md5";
    val digestPassword = "HG58YZ3CR9";

    @throws(classOf[Exception])
    def main(args: Array[String]): Unit = {

        val text = "password";

        val codedtext = encrypt(text);
        val decodedtext = decrypt(codedtext);

        println("Orignal: " + text);
        println("Encrypted: " + codedtext); // this is a byte array, you'll just see a reference to an array
        println("Decrypted: " + decodedtext); // This correctly shows "kyle boon"
    }
    @throws(classOf[Exception])
    def setupSecretKey(): SecretKey = {
        val md = MessageDigest.getInstance(digestName);
        val digestOfPassword = md.digest(digestPassword.getBytes());
        val keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for ( j <- 0 to 8; k <- 16 until 24) {
            keyBytes(+k) = keyBytes(+j);
        }

        return new SecretKeySpec(keyBytes, "DESede");
    }

    @throws(classOf[Exception])
    def setupCipher(optMode: Int): Cipher = {
      val key = setupSecretKey();
      val iv = new IvParameterSpec(new Array[Byte](8));
      val cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      cipher.init(optMode, key, iv);
      return cipher;
    }

    @throws(classOf[Exception])
    def encrypt(message: String): Array[Byte] = {
        val cipher = setupCipher(Cipher.ENCRYPT_MODE);

        val plainTextBytes = message.getBytes()
        val cipherText = cipher.doFinal(plainTextBytes);

        return cipherText;
    }

    @throws(classOf[Exception])
    def decrypt(message: Array[Byte]): String = {
        val decipher = setupCipher(Cipher.DECRYPT_MODE);

        val plainText = decipher.doFinal(message);

        return new String(plainText, "UTF-8");
    }
}
