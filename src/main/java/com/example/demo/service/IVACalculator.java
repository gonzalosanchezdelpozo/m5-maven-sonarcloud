package com.example.demo.service;

public class IVACalculator {

    public double calculateIVA(double amount){
        return amount * 0.21;
    }

    void deleteNullTest(){
        SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
        boolean result = service.delete(null);
    }
}
