package com.stefan.password_generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Handles API requests for generating passwords and saving accepted passwords.
 */
@RestController
@RequestMapping("/api")
public class PasswordController {

    /** Repository used to persist accepted password hashes. */
    @Autowired
    private PasswordRepository repository;

    /**
     * Generates a password from the requested character groups.
     * If no groups are selected, all supported groups are used.
     */
    @GetMapping("/generate")
    public  Map<String, String> generatePassword(@RequestParam int length, @RequestParam boolean uppercase, @RequestParam boolean lowercase,
    @RequestParam boolean numbers, @RequestParam boolean symbols){
        // SecureRandom is intended for generating values that should be difficult to predict.
        SecureRandom sec_rand = new SecureRandom();

        // Build the character pool from the selected groups.
        StringBuilder availableChars = new StringBuilder();

        if(uppercase){
            availableChars.append("ABCDEFGHIJKLMOPQRSTUVWXYZ");
        }
        if(lowercase){
            availableChars.append("abcdefghijklmnopqrstuvwxyz");
        }
        if(numbers){
            availableChars.append("0123456789");
        }
        if(symbols){
            availableChars.append("!@#$%^&*()_+-=<>?`~/|[]{}.,:;'");
        }
        if(availableChars.isEmpty()){
            // Keep generation usable when the request selects no character groups.
            availableChars.append("ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=<>?`~/|[]{}.,:;'");
        }

        String chars = availableChars.toString();
        StringBuilder password = new StringBuilder();

        for(int i=0;i<length;i++){
            int index = sec_rand.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        // Spring serializes this map as a JSON response.
        return Map.of("password", password.toString());
    }

    /** Hashes the submitted password and stores the hash instead of the original value. */
    @PostMapping("/accept")
    public Map<String, String> acceptPassword(@RequestBody Map<String, String> payload){
        String chosenPass = payload.get("password");

        try{
            // Encode the submitted text as UTF-8 before computing its SHA-256 digest.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(chosenPass.getBytes(StandardCharsets.UTF_8));
            String hashPass = Base64.getEncoder().encodeToString(hashBytes);

            PasswordEntity entity = new PasswordEntity();
            entity.SetPassword(hashPass);
            repository.save(entity);

            return Map.of("status", "saved");
        }catch (Exception e){
            return Map.of("status", "error", "message", "Error at securing the password");
        }

    }
}
