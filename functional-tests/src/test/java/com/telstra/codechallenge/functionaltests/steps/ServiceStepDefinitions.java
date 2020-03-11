package com.telstra.codechallenge.functionaltests.steps;

import static com.jayway.jsonassert.JsonAssert.with;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsArray;
import org.hamcrest.core.IsInstanceOf;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@Slf4j
@SuppressWarnings("unchecked")
@ContextConfiguration("classpath:cucumber.xml")
public class ServiceStepDefinitions extends StepDefinitionBase {

  @Before
  public void beforeScenario() {
    log.info("Clearing scenario data map.");
    scenarioData.clear();
  }

  @Given("^I get the service uri (.+)$")
  public void whenIGetTheServiceUri(String serviceUri) {
    HttpHeaders httpHeaders = (HttpHeaders)scenarioData.get("httpHeaders");
    HttpMethod httpMethod = HttpMethod.GET;
    scenarioData.put("responseEntity", this.executeRestRequest(serviceUri, httpMethod, httpHeaders));
  }

  @Then("^the service uri returns status code (\\d+)$")
  public void theServiceUriReturnsStatusCode(int expectedCode) {
    assertEquals(expectedCode, getScenarioResponseEntity().getStatusCodeValue());
  }



  /** check the response content type. */
  @And("^the content type is (json|xml|plain text|any)$")
  public void theContentTypeIs(String contentType) {
    ResponseEntity<String> responseEntity = getScenarioResponseEntity();
    assertNotNull(responseEntity.getHeaders().getContentType());
    if ("json".equalsIgnoreCase(contentType)) {
      assertTrue(responseEntity.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON));
    }
    if ("xml".equalsIgnoreCase(contentType)) {
      assertTrue(responseEntity.getHeaders().getContentType().includes(MediaType.APPLICATION_XML));
    }
    if ("text".equalsIgnoreCase(contentType)) {
      assertTrue(responseEntity.getHeaders().getContentType().includes(MediaType.TEXT_PLAIN));
    }
  }

  /**
   * Check the json path in the request is a required type.
   *
   * @param path - The json path to the node of interest.
   * @param type - The expected type of the node.
   * @throws IOException from getObjectMapper
   */
  @And("^the body has json path (.*) of type (array|boolean|numeric|object|string)$")
  public void theBodyHasJsonPathThatHasType(String path, String type) throws IOException {

    switch (type) {
      case "array":
        assertThat(
            getScenarioResponseEntity().getBody(), hasJsonPath(path, Matchers.instanceOf(List.class)));
        break;
      case "boolean":
        assertThat(
            getScenarioResponseEntity().getBody(), hasJsonPath(path, Matchers.instanceOf(Boolean.class)));
        break;
      case "object":
        assertThat(
            getScenarioResponseEntity().getBody(), hasJsonPath(path, Matchers.instanceOf(Object.class)));
        break;
      case "string":
        assertThat(
            getScenarioResponseEntity().getBody(), hasJsonPath(path, Matchers.instanceOf(String.class)));
        break;
      case "numeric":
        assertThat(
            getScenarioResponseEntity().getBody(), hasJsonPath(path, Matchers.instanceOf(Number.class)));
        break;
      default:
        fail("Not a recognised type.");
    }
  }

  /** Make an assertion on the string value of a given JSON path. */
  @And("^the body has json path (.+) that is equal to (.+)$")
  public void theBodyHasJsonPathThatIsEqualTo(String path, String value) {
    assertThat(
        getScenarioResponseEntity().getBody(), hasJsonPath(path, Matchers.hasToString(value)));
  }

}
