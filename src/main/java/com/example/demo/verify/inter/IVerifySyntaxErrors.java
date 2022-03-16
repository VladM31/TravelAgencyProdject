package com.example.demo.verify.inter;

public interface IVerifySyntaxErrors {

    public boolean hasProblemInPassword(String password);

    public boolean hasProblemInUsername(String username);

    public boolean hasProblemInEmail(String email);

}
