package com.telstra.codechallenge.git;

import static com.telstra.codechallenge.utils.DateUtil.getLastLocalDate;
import static com.telstra.codechallenge.utils.DateUtil.getYYYYMMDD;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class GitSearchResponseService {

  public static final String SORT_KEY = "sort";
  public static final String QUERY = "q";
  public static final String ORDER_KEY = "order";
  public static final String PER_PAGE_LIMIT = "per_page";
  public static final String DESC = "desc";
  public static final String STARS = "stars";
  public static final String CREATED_DATE = "created:";

  private final RestTemplate restTemplate;

  @Value("${git.base.url}")
  private String gitBaseUrl;

  @Autowired
  public GitSearchResponseService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  public GitSearchResponse getHottestRepos(int noOfRepos, int lastDays) {

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

    UriComponentsBuilder builder = getUriComponentsBuilder(gitBaseUrl, noOfRepos, lastDays);

    return this.restTemplate.exchange(
        builder.build().toString(), HttpMethod.GET, new HttpEntity<>(headers),
        GitSearchResponse.class).getBody();
  }

  private UriComponentsBuilder getUriComponentsBuilder(String url, int noOfRepos, int lastDays) {
    return UriComponentsBuilder.fromHttpUrl(url)
          .queryParam(QUERY, CREATED_DATE + getYYYYMMDD(getLastLocalDate(lastDays)))
          .queryParam(SORT_KEY, STARS)
          .queryParam(ORDER_KEY, DESC)
          .queryParam(PER_PAGE_LIMIT, noOfRepos);
  }

}
