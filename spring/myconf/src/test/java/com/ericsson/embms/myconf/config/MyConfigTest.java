package com.ericsson.embms.myconf.config;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyConfigTest {

    @Autowired
    private MyConfig cfg;

    @Test
    public void testItShouldLoadWithSuccess() {

        assertThat(cfg.getCcc(), is("11111"));
    }

}
