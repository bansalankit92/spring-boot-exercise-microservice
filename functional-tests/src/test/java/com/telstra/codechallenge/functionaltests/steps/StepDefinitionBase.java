package com.telstra.codechallenge.functionaltests.steps;

import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("SameParameterValue")
public abstract class StepDefinitionBase {

  protected static final String RESPONSE_ENTITY = "responseEntity";
  protected static final String HTTP_HEADERS = "httpHeaders";
  protected static final String REQUEST_PARAMETER_MAP = "requestParameterMap";
  protected static Map<String, Object> scenarioData = new ConcurrentHashMap<>();

  @Value("${test.server.transport:http}")
  protected String transport;
  @Value("${test.server.host:localhost}")
  protected String host;
  @Value("${test.server.port:8080}")
  protected String port;
  @Value("${test.server.context:/}")
  protected String context;
  private ObjectMapper objectMapper;

  protected ObjectMapper getObjectMapper() {
    if (null == objectMapper) {
      objectMapper = new ObjectMapper();
    }
    return objectMapper;
  }

  protected ResponseEntity<String> executeRestRequest(
      String serviceUri, HttpMethod method, HttpHeaders httpHeaders) {
    String url = parseUrl(serviceUri);
    return getStringResponseEntity(method, httpHeaders, url);
  }

  protected ResponseEntity<String> getStringResponseEntity(
      HttpMethod method, HttpHeaders httpHeaders, String url) {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
    try {
      return restTemplate.exchange(url, method, entity, String.class);
    } catch (HttpStatusCodeException e) {
      return new ResponseEntity<>(
          e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
    }
  }

  protected ResponseEntity<String> getStringResponseEntity(
      HttpMethod method, HttpHeaders httpHeaders, String url, JsonNode jsonBody) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders hdrs = new HttpHeaders();
    if (httpHeaders != null) {
      hdrs = new HttpHeaders();
    }
    hdrs.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), hdrs);
    try {
      return rt.exchange(url, method, entity, String.class);
    } catch (HttpStatusCodeException e) {
      return new ResponseEntity<>(
          e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
    }
  }

  protected HttpHeaders getScenarioHttpHeaders() {
    scenarioData.computeIfAbsent(HTTP_HEADERS, k -> new HttpHeaders());
    return (HttpHeaders) scenarioData.get(HTTP_HEADERS);
  }

  protected ResponseEntity<String> getScenarioResponseEntity() {
    @SuppressWarnings(value = "unchecked")
    ResponseEntity<String> responseEntity =
        (ResponseEntity<String>) scenarioData.get(RESPONSE_ENTITY);

    if (responseEntity == null) {
      fail("Called for ResponseEntity but it is null!");
    }
    return responseEntity;
  }

  protected String createAuthHeader(String username, String password) {
    String auth = username + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
    return "Basic " + new String(encodedAuth, StandardCharsets.US_ASCII);
  }

  /** Add the parameter and value to a parameterMap for later retrieval via getRequestParamaters. */
  protected void addRequestParameters(String parameter, String value) {
    scenarioData.computeIfAbsent(REQUEST_PARAMETER_MAP, k -> new HashMap<>());
    Map<String, String> parameterMap =
        (Map<String, String>) scenarioData.get(REQUEST_PARAMETER_MAP);
    parameterMap.put(parameter, value);
  }

  /** Returns a string of request parameters obtained from the scenarioData paramaterMap. */
  protected String getRequestParameters() {
    Map<String, String> parameterMap =
        (Map<String, String>) scenarioData.get(REQUEST_PARAMETER_MAP);
    if (parameterMap == null) {
      return "";
    }

    StringJoiner paramJoiner = new StringJoiner("&");
    parameterMap.keySet().forEach(e -> paramJoiner.add(e + "=" + parameterMap.get(e)));
    return "?" + paramJoiner.toString();
  }

  private String parseUrl(String serviceUri) {
    return transport + "://" + host + ":" + port + context + serviceUri;
  }

}
