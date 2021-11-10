package com.example.demo.service;

public class IRPFCalculator {

    public double calculateIRPF(double amount){
        return amount * 0.15;
    }
    public double calculateIVA(double amount){
        return amount * 0.21;
    }
}
