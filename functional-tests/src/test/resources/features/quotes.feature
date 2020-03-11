  Feature: As an api user I want to retrieve some spring boot quotes

    Scenario: Get a random quote
      When I get the service uri quotes/random
      Then the service uri returns status code 200
      And the content type is json
      And the body has json path $ of type object
      And the body has json path $.type of type string
      And the body has json path $.type that is equal to success
      And the body has json path $.value of type object
      And the body has json path $.value.id of type numeric
      And the body has json path $.value.quote of type string

    Scenario: Get all the quotes
      When I get the service uri quotes
      Then the service uri returns status code 200
      And the content type is json
      And the body has json path $.[0] of type object
      And the body has json path $.[0].type of type string
      And the body has json path $.[0].type that is equal to success
      And the body has json path $.[0].value of type object
      And the body has json path $.[0].value.id of type numeric
      And the body has json path $.[0].value.quote of type string
