Feature: Get customer by id

  Scenario: Get customer with valid id
    Given customer exists in db
    When client calls GET 'v1/customer'
    Then client receives status code 200
    And client gets customer with correct id