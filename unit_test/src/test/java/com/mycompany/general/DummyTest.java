package com.mycompany.general;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class DummyTest {

    @Test
    public void test_1_1(){
        Assert.assertEquals(1,1);
        Assert.assertThat(1, is(1));
    }
}
