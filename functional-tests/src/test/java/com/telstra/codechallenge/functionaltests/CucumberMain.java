package com.telstra.codechallenge.functionaltests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"classpath:features/"},
    format = {"html:target/cucumber-html-report",
              "junit:target/cucumber-junit-report.xml"},
    tags = {"~@ignore"})
public class CucumberMain {

}
