package com.ZTED.config;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Class Name: Argon2Hasher
 * Package: com.ZTED.config
 * Description:
 *
 * @Author: Ricks
 * @Create Date: 30/10/2023 12:58 am
 * @Version 1.0
 */
public class Argon2Hasher {

    public static byte[] hashPassword(char[] password) {
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withSalt(generateSalt()) // you should generate a random salt
                .withParallelism(1) // number of threads and compute lanes to use
                .withMemoryAsKB(65536) // specify memory usage in KB
                .withIterations(4);

        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());

        byte[] result = new byte[32]; // size of the output hash in bytes
        gen.generateBytes(password, result, 0, result.length);
        return result;
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes salt
        random.nextBytes(salt);
        return salt;
    }

    public static boolean verifyPassword(char[] password, byte[] hash) {
        byte[] hashToVerify = hashPassword(password);
        // Compare hashToVerify with the stored hash
        // ...
        return Arrays.equals(hashToVerify, hash);
    }
}
