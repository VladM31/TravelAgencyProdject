package com.example.demo.tempClasses.verify;

import com.example.demo.tempClasses.verify.verify.inter.IVerifySyntaxErrors;
import org.springframework.stereotype.Component;

@Component
public class VerifyTempSyntaxErrors implements IVerifySyntaxErrors {
    @Override
    public boolean hasProblemInPassword(String password) {
        return this.checkString(password);
    }

    @Override
    public boolean hasProblemInUsername(String username) {
        return this.checkString(username);
    }

    @Override
    public boolean hasProblemInEmail(String email) {
        return this.checkString(email);
    }

    private boolean checkString(String str) {
        if (str == null) {
            return true;
        }
        return str.isEmpty();
    }
}
