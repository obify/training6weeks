package com.mycompany.calculatorapp;

import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;

public class AreaCalculatorTest {

    AreaCalculator areaCalculator;
    DecimalFormat df = new DecimalFormat("#.####");

    @Before
    public void setup(){
        areaCalculator = new AreaCalculator();
    }

    @Test
    public void test_calculateAreaOfCircle(){
        Double result = areaCalculator.calculateAreaOfCircle(Double.valueOf(2.20));
        result = Double.valueOf(df.format(result));
        assertEquals(Double.valueOf(15.1976),result);
    }

    @Test
    public void test_calculateAreaOfRectangle(){
        Double result = areaCalculator.calculateAreaOfRectangle(2.2, 2.5);
        assertEquals(Double.valueOf(5.5),result);
    }

    @Test
    public void test_calculateAreaOfSquare(){
        Double result = areaCalculator.calculateAreaOfSquare(2.2);
        result = Double.valueOf(df.format(result));
        assertEquals(Double.valueOf(4.84),result);
    }
}
