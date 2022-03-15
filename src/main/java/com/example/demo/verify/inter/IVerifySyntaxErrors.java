package com.example.demo.verify.inter;

public interface IVerifySyntaxErrors {

    public boolean checkOutPassword(String password);

    public boolean checkOutUsername(String username);

    public boolean checkOutEmail(String email);

}
