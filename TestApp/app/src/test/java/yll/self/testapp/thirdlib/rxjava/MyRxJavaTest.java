package yll.self.testapp.thirdlib.rxjava;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by yll on 17/9/26.
 */
public class MyRxJavaTest {
    MyRxJava myRxJava;

    @Before
    public void setUp() throws Exception {
        myRxJava = new MyRxJava();
    }

    @Test
    public void testCreate() throws Exception {
        myRxJava.testCreate();
    }

    @Test
    public void testJust() throws Exception {
        myRxJava.testJust();
    }

    @Test
    public void testFrom() throws Exception {
        myRxJava.testFrom();
    }

    @Test
    public void testActions() throws Exception{
        myRxJava.testActions();
    }

    @Test
    public void printStringArray() throws Exception{
        myRxJava.printStringArray();
    }

    @Test
    public void testScheduler1() throws Exception{
        myRxJava.testScheduler1();
    }

    @Test
    public void testFlatMap() throws Exception{
        myRxJava.testFlatMap();
    }

    @Test
    public void testCompose() throws Exception{
        myRxJava.testCompose();
    }

    @Test
    public void testScheduler2() throws Exception{
        myRxJava.testScheduler2();
    }

    @Test
    public void testDoOnSubscribe() throws Exception{
        myRxJava.testDoOnSubscribe();
    }

}