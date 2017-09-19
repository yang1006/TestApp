package yll.self.testapp.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yll on 17/9/19.
 */
public class CalculatorUtilsTest {

    CalculatorUtils calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new CalculatorUtils();
    }

    @Test
    public void sum() throws Exception {
        assertEquals(6d, calculator.sum(1d, 5d), 0d);
    }

    @Test
    public void substract() throws Exception {
        assertEquals(1d, calculator.substract(5d, 4d), 0d);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(2d, calculator.divide(10d, 5d), 0d);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(10d, calculator.multiply(2d, 5d), 0d);
    }

}