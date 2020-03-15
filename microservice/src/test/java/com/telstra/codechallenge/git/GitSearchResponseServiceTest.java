package com.telstra.codechallenge.git;

import static com.telstra.codechallenge.git.GitSearchResponseService.CREATED_DATE;
import static com.telstra.codechallenge.git.GitSearchResponseService.DESC;
import static com.telstra.codechallenge.git.GitSearchResponseService.ORDER_KEY;
import static com.telstra.codechallenge.git.GitSearchResponseService.PER_PAGE_LIMIT;
import static com.telstra.codechallenge.git.GitSearchResponseService.QUERY;
import static com.telstra.codechallenge.git.GitSearchResponseService.SORT_KEY;
import static com.telstra.codechallenge.git.GitSearchResponseService.STARS;
import static com.telstra.codechallenge.utils.DateUtil.getLastLocalDate;
import static com.telstra.codechallenge.utils.DateUtil.getYYYYMMDD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(MockitoJUnitRunner.class)
public class GitSearchResponseServiceTest {

  private static final String HTTP_GITURL = "http://giturl";
  private static final int NO_OF_REPOS = 10;
  private static final int LAST_DAYS = 7;
  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private GitSearchResponseService gitService;

  @Before
  public void setup() {
    ReflectionTestUtils.setField(gitService, "gitBaseUrl", HTTP_GITURL);
  }

  @Test
  public void getHottestRepoForSuccess()
      throws Exception {
    GitSearchResponse searchResponse = new GitSearchResponse();
    searchResponse.getItems().add(getGitRepoDetail());
    Mockito.when(restTemplate
        .exchange(getUriComponentsBuilder(HTTP_GITURL, NO_OF_REPOS, LAST_DAYS).build().toString(),
            HttpMethod.GET, new HttpEntity<>(getHttpHeaders()),
            GitSearchResponse.class))
        .thenReturn(new ResponseEntity<>(searchResponse, HttpStatus.OK));

    GitSearchResponse response = gitService.getHottestRepos(NO_OF_REPOS, LAST_DAYS);

    Assert.assertEquals(searchResponse, response);
  }

  private GitRepoDetail getGitRepoDetail() {
    GitRepoDetail gitRepoDetail =  new GitRepoDetail();
    gitRepoDetail.setDesciption("some");
    gitRepoDetail.setName("name");
    return gitRepoDetail;
  }

  @Test(expected = Exception.class)
  public void getHottestRepoForException()
      throws Exception {
    GitSearchResponse searchResponse = new GitSearchResponse();

    Mockito.when(restTemplate
        .exchange(
            getUriComponentsBuilder("http://Wrongurl", NO_OF_REPOS, LAST_DAYS).build().toString(),
            HttpMethod.GET, new HttpEntity<>(getHttpHeaders()),
            GitSearchResponse.class))
        .thenReturn(new ResponseEntity<>(searchResponse, HttpStatus.NOT_FOUND));
    gitService.getHottestRepos(NO_OF_REPOS, LAST_DAYS);

  }

  private HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }

  private UriComponentsBuilder getUriComponentsBuilder(String url, int noOfRepos, int lastDays) {
    return UriComponentsBuilder.fromHttpUrl(url)
        .queryParam(QUERY, CREATED_DATE + getYYYYMMDD(getLastLocalDate(lastDays)))
        .queryParam(SORT_KEY, STARS)
        .queryParam(ORDER_KEY, DESC)
        .queryParam(PER_PAGE_LIMIT, noOfRepos);
  }
}