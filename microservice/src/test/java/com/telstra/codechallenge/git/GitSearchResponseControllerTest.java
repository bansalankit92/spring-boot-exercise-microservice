package com.telstra.codechallenge.git;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.telstra.codechallenge.MicroServiceMain;
import com.telstra.codechallenge.advice.ExceptionControllerAdvice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {GitSearchResponseController.class})
//@WebAppConfiguration

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {MicroServiceMain.class})
public class GitSearchResponseControllerTest {

  private static final String GIT_HOTTEST_10_API_URL = "/git/hottest/10";
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @InjectMocks
  private GitSearchResponseController gitSearchResponseController;

  @Mock
  private GitSearchResponseService gitSearchResponseService;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(gitSearchResponseController)
        .setControllerAdvice(new ExceptionControllerAdvice())
        .build();
  }

  @Test
  public void getHottestRepos_success() throws Exception {
    when(gitSearchResponseService.getHottestRepos(10, 10)).thenReturn(new GitSearchResponse());

    mockMvc.perform(get(GIT_HOTTEST_10_API_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
    ;


  }

  @Test
  public void getHottestRepos_failure() throws Exception {
    when(gitSearchResponseService.getHottestRepos(10, 7)).thenThrow(HttpClientErrorException.class);

    mockMvc.perform(get(GIT_HOTTEST_10_API_URL)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
    ;


  }
}