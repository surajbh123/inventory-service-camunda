package com.example.loanapplication.model;

public class Applicant {

    private Integer creditScore;
    private Double debtToIncomeRatio;
    private String existingDefaults;
    private Double loanAmountRatio;

    public Applicant() {
    }

    public Applicant(Integer creditScore, Double debtToIncomeRatio, String existingDefaults, Double loanAmountRatio) {
        this.creditScore = creditScore;
        this.debtToIncomeRatio = debtToIncomeRatio;
        this.existingDefaults = existingDefaults;
        this.loanAmountRatio = loanAmountRatio;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Double getDebtToIncomeRatio() {
        return debtToIncomeRatio;
    }

    public void setDebtToIncomeRatio(Double debtToIncomeRatio) {
        this.debtToIncomeRatio = debtToIncomeRatio;
    }

    public String getExistingDefaults() {
        return existingDefaults;
    }

    public void setExistingDefaults(String existingDefaults) {
        this.existingDefaults = existingDefaults;
    }

    public Double getLoanAmountRatio() {
        return loanAmountRatio;
    }

    public void setLoanAmountRatio(Double loanAmountRatio) {
        this.loanAmountRatio = loanAmountRatio;
    }
}
