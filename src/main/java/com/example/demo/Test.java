package com.example.demo;

public class Test
{
    private boolean error;
    private boolean logout;

    public boolean isError() {
        return error;
    }

    public boolean isLogout() {
        return logout;
    }
    public void setError(boolean error) {
        this.error = error;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public Test(boolean error, boolean logout) {
        this.error = error;
        this.logout = logout;
    }

    public Test() {
    }
}
