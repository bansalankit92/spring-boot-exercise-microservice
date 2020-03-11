# Telstra Spring Boot Coding Exercise #

A place to do some spring boot coding exercises.


## Structure ##

The `micoservice` module produces a spring boot application. The `functional-tests` module starts 
the spring boot application defined in the `microservice` module and runs cucumber tests defined in
`functional-tests/src/test/resources/features` to verify behaviour.

The step definitions for the cucumber tests are defined in 
`functional-tests/src/test/java/com.telstra.codechallenge.functionaltests/steps/ServiceStepDefinitions.java`.


## Instructions ##

Select one of the exercises below and add the required behaviour to the spring boot application. 

Add any code and libraries you need.

Refactor any of the existing code if needed.

Add cucumber tests to the `functional-tests` module to verify the behaviour adding new step 
definitions if required.


## Exercises ##

See https://gist.github.com/bartonhammond/0a19da4c24c0f644ae38


### Find the hottest repositories created in the last week ###

Use the [GitHub API][1] to expose an endpoint in this microservice the will get a list of the 
highest starred repositories in the last week.

The endpoint should accept a parameter that sets the number of repositories to return.

The following fields should be returned:

      html_url
      watchers_count
      language
      description
      name

Cucumber tests should be used to verify the behaviour


### Find the oldest user accounts with zero followers ###

Use the [GitHub API][1] to expose an endpoint in this microservice that will find the oldest 
accounts with zero followers.

The endpoint should accept a parameter that sets the number of accounts to return.

The following fields should be returned:

      id
      login
      html_url

Cucumber tests should be used to verify the behaviour



[1]: http://developer.github.com/v3/search/#search-repositories
