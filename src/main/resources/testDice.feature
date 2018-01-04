Feature:  test dice outcome

  Scenario: Test with 3
    Given I have a dice cast at 3
    When I query the oracle
    Then I expect the oracle to return 2

  Scenario: Test with 5
    Given I have a dice cast at 5
    When I query the oracle
    Then I expect the oracle to return 4

  Scenario: Test with another number
    Given I have a dice cast at 0
    When I query the oracle
    Then I expect the oracle to return 0

  Scenario: Test with 6 dice, all different numbers
    Given I have a dice cast at 1
    And I have a dice cast at 2
    And I have a dice cast at 3
    And I have a dice cast at 4
    And I have a dice cast at 5
    And I have a dice cast at 6
    When I query the oracle
    Then I expect the oracle to return 6

  Scenario: Test with another number
    Given I have a dice cast at 2
    When I query the oracle
    Then I expect the oracle to return 0