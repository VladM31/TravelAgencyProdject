package com.example.demo.tempClasses.verify;

import com.example.demo.verify.inter.IVerifySyntaxErrors;
import org.springframework.stereotype.Component;

@Component
public class VerifyTempSyntaxErrors implements IVerifySyntaxErrors {
    @Override
    public boolean checkOutPassword(String password) {
        return this.checkString(password);
    }

    @Override
    public boolean checkOutUsername(String username) {
        return this.checkString(username);
    }

    @Override
    public boolean checkOutEmail(String email) {
        return this.checkString(email);
    }

    private boolean checkString(String str) {
        if (str == null) {
            return true;
        }
        return !str.isEmpty();
    }
}
