package com.example.demo.forms;

public class ChooseSignUpForm {
    private static String decsribeCustomer;
    private static String decsribeTravelAgency;

    private boolean chooseCustomer;
    private boolean chooseTravelAgency;

    public static String getDecsribeCustomer() {
        return decsribeCustomer;
    }

    public static void setDecsribeCustomer(String decsribeCustomer) {
        ChooseSignUpForm.decsribeCustomer = decsribeCustomer;
    }

    public static String getDecsribeTravelAgency() {
        return decsribeTravelAgency;
    }

    public static void setDecsribeTravelAgency(String decsribeTravelAgency) {
        ChooseSignUpForm.decsribeTravelAgency = decsribeTravelAgency;
    }

    public boolean isChooseCustomer() {
        return chooseCustomer;
    }

    public void setChooseCustomer(boolean chooseCustomer) {
        this.chooseCustomer = chooseCustomer;
    }

    public boolean isChooseTravelAgency() {
        return chooseTravelAgency;
    }

    public void setChooseTravelAgency(boolean chooseTravelAgency) {
        this.chooseTravelAgency = chooseTravelAgency;
    }

    public ChooseSignUpForm(boolean chooseCustomer, boolean chooseTravelAgency) {
        this.chooseCustomer = chooseCustomer;
        this.chooseTravelAgency = chooseTravelAgency;
    }

    public ChooseSignUpForm() {
    }
}
