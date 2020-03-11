  Feature: As a developer i want to know if my spring boot application is running

    Scenario: Is the health uri available and status=UP
      When I get the service uri health
      Then the service uri returns status code 200
      And the content type is json
      And the body has json path $ of type object
      And the body has json path $.status of type string
      And the body has json path $.status that is equal to UP

    Scenario: Is the info uri available and returning data
      When I get the service uri info
      Then the service uri returns status code 200
      And the content type is json
      And the body has json path $ of type object
      And the body has json path $.build of type object
      And the body has json path $.build.version of type string
      And the body has json path $.build.artifact of type string
      And the body has json path $.build.name of type string
      And the body has json path $.build.group of type string
      And the body has json path $.build.time of type numeric
      And the body has json path $.build.name that is equal to microservice