Feature: Book flights on blazedemo.com

  Scenario: Check the find flights feature
    Given I launch the blazedemo
    When I select the depature city
    And I select the destination city
    Then I click on find flights button

  Scenario: Reserve the flight
    Given I am on reserve page
    When I see the flight details
    Then I click on Choose this flight

  Scenario: Purchase the flight ticket
    Given I am on purchase page
    When I will the passanger details
    Then I click on purchase flight

  Scenario: Flight ticket confirmation
    Given I am on confirmation page
    When I see the confirmation details
    Then I see the confirmation Id
