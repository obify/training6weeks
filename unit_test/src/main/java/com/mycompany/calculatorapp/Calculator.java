package com.mycompany.calculatorapp;

public class Calculator {

    public Integer add(Integer num1, Integer num2){
        Integer result = num1 + num2;
        return result;
    }

    public Double subtract(Double num1, Double num2){

        if(num1 > num2){
            return num1-num2;
        }
        return num2-num1;
    }

    public Double multiply(Double num1, Double num2){
        Double result = num1 * num2;
        return result;
    }

    public Double division(Double num1, Double num2){

        if(num2 == 0){
            return -1.0;
        }
        return num1/num2;
    }
}
