 Feature: As a developer i want to test the helloworld uri

    Scenario: Is the hello uri available and functioning
      When I get the service uri hello
      Then the service uri returns status code 200
      And the content type is json
      And the body has json path $ of type object
      And the body has json path $.id of type numeric
      And the body has json path $.content of type string
      And the body has json path $.content that is equal to Hello, World!