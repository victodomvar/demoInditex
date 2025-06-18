Feature: tests for prices

  Background:
    * def baseUrl = karate.properties['baseUrl']
    * url baseUrl
    * def path = '/prices'

  @positive
  Scenario: 14th June 10:00 - Product 35455, Brand 1
    Given path path
    And param date = '2020-06-14T10:00:00Z'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.productId == 35455
    And match response.brandId == 1
    And match response.price == 35.50

  @positive
  Scenario: 14th June 16:00 - Product 35455, Brand 1
    Given path path
    And param date = '2020-06-14T16:00:00Z'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 25.45

  @positive
  Scenario: 14th June 21:00 - Product 35455, Brand 1
    Given path path
    And param date = '2020-06-14T21:00:00Z'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 35.50

  @positive
  Scenario: 15th June 10:00 - Product 35455, Brand 1
    Given path path
    And param date = '2020-06-15T10:00:00Z'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 30.50

  @positive
  Scenario: 16th June 21:00 - Product 35455, Brand 1
    Given path path
    And param date = '2020-06-16T21:00:00Z'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 38.95

  @negative
  Scenario: Missing productId parameter should return 400
    Given path path
    And param date = '2020-06-14T10:00:00Z'
    And param brandId = 1
    When method get
    Then status 400

  @negative
  Scenario: Missing date parameter should return 400
    Given path path
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 400

  @negative
  Scenario: No applicable price found should return 404
    Given path path
    And param date = '2021-01-01T00:00:00Z'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 404

