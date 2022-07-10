package org.austral.ing.lab1.model;

public class Token {
    private final int token;

    private Token(int token) {
        this.token = token;
    }

    public static Token create(int token) {
        return new Token(token);
    }

    public int getToken() {
        return token;
    }
}
