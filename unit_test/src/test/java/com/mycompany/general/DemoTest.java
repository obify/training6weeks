package com.mycompany.general;

import org.junit.*;

import static org.hamcrest.Matchers.is;

public class DemoTest {

    @Before//2  5
    public void beforeTest(){
        System.out.println("exc before every test");
    }

    @After//4   7
    public void afterTest(){
        System.out.println("exc after every test");
    }

    @Test//3
    public void testNameComparision(){
        Assert.assertEquals("John", "John");
        System.out.println("test 3");
        Assert.assertThat(666, is(666));
    }
    @Test//6
    //@Ignore
    public void testSalaryComparision(){
        //Assert.assertEquals(66656.67, 777.9);
        System.out.println("test 6");
        Assert.assertThat(666, is(66));
    }

    @BeforeClass//1
    public static void beforeClassTest(){
        System.out.println("exc before class all test");
    }

    @AfterClass//8
    public static void afterClassTest(){
        System.out.println("exc after class all test");
    }
}
