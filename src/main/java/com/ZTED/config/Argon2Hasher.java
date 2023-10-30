package com.ZTED.config;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import java.util.Arrays;

public class Argon2Hasher {

    // Hash the password and return both the hash and the salt
    public static HashResult hashPassword(char[] password) {
        byte[] salt = generateSalt();

        byte[] hash = hashPasswordWithSalt(password, salt);

        return new HashResult(hash, salt);
    }

    // Hash the password using a given salt
    private static byte[] hashPasswordWithSalt(char[] password, byte[] salt) {
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withSalt(salt)
                .withParallelism(1)
                .withMemoryAsKB(65536)
                .withIterations(4);

        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());

        byte[] result = new byte[32];
        gen.generateBytes(password, result, 0, result.length);

        return result;
    }

    // Verify the password using its hash and salt
    public static boolean verifyPassword(char[] password, byte[] hash, byte[] salt) {
        byte[] hashToVerify = hashPasswordWithSalt(password, salt);
        return Arrays.equals(hashToVerify, hash);
    }

    // Generate a new random salt
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // A simple class to hold both the hash and the salt
    public static class HashResult {
        public final byte[] hash;
        public final byte[] salt;

        public HashResult(byte[] hash, byte[] salt) {
            this.hash = hash;
            this.salt = salt;
        }
    }
}
