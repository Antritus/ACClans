package com.gmail.antcoreservices.acclans.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class StringUtils {
    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String betterName(String name) {
        name = name.toLowerCase();
        String[] splitName = name.split("_");
        StringBuilder builder = new StringBuilder();
        for (String s : splitName) {
            builder.append(
                    s.replaceFirst(((Character)s.charAt(0)).toString(), ((Character)s.charAt(0)).toString().toUpperCase())
            ).append(" ");
        }
        builder.replace(builder.length(), builder.length(), "");
        return builder.toString();
    }

    public static String randomisedCode(int length){
        String alphabet = "abcdefghijklmnpqrstuvwxyz";
        return randomisedCode(alphabet, length);
    }
    public static String randomisedCode(String letters, int length){
        Random r = new Random();
        String alphabet = letters;
        String[] code = new String[length];
        for (int i = 0; i < length; i++) {
            code[i] = String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return Arrays.stream(code).toString().replaceAll(",", "").replaceAll(" ", "");
    }
    public static void main(String[] args){
        System.out.println("abcdefghijklmnpqrstuvwxyz".toUpperCase());
    }

    @Contract(pure = true)
    public static @NotNull String getAlphabetLowerCase(){
        return "abcdefghijklmnpqrstuvwxyz";
    }
    @Contract(pure = true)
    public static @NotNull String getAlphabetUppercase(){
        return "ABCDEFGHIJKLMNPQRSTUVWXYZ";
    }

}
