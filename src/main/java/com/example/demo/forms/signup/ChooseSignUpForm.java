package com.example.demo.forms.signup;

public class ChooseSignUpForm {
    private static String decsribeCustomer = "Таки купить";
    private static String decsribeTravelAgency = "Таки продать";

    private Boolean choose;
    private boolean error;
    private boolean hello;

    public static void setDecsribeCustomer(String decsribeCustomer) {
        ChooseSignUpForm.decsribeCustomer = decsribeCustomer;
    }

    public static void setDecsribeTravelAgency(String decsribeTravelAgency) {
        ChooseSignUpForm.decsribeTravelAgency = decsribeTravelAgency;
    }

    public void setChoose(Boolean choose) {
        this.choose = choose;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ChooseSignUpForm(Boolean choose, boolean error, boolean hello) {
        this.choose = choose;
        this.error = error;
        this.hello = hello;
    }

    public void setHello(boolean hello) {
        this.hello = hello;
    }

    public static String getDecsribeCustomer() {
        return decsribeCustomer;
    }

    public static String getDecsribeTravelAgency() {
        return decsribeTravelAgency;
    }

    public Boolean getChoose() {
        return choose;
    }

    public boolean isError() {
        return error;
    }

    public boolean isHello() {
        return hello;
    }

    public ChooseSignUpForm() {
    }

    public boolean isEmpty()
    {
        return this.choose == null;
    }

    public boolean isCustomer()
    {
        return this.choose;
    }



    public void turnOnError()
    {
        this.error = true;
        this.hello = false;
    }
}
