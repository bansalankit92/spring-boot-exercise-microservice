package com.telstra.codechallenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroServiceMainTest {

  @Test
  public void testContextLoading() {
    // Just by having this test will start the context and ensure it loads
  }
}
