Feature: Get customer by id

  Scenario: Get customer by valid id
    Given db contains an user with valid id
    When client makes a call to GET 'v1/customer' with id
    Then client receives status code 200
    And client id is valid

  Scenario: Get customer by invalid id
    When client makes a call to GET 'v1/customer' with invalid
    Then client receives status code 404