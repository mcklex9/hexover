Feature: Account verification

  Scenario: Create account and start soft check
    When account created by user
    Then account is under fraud check

  Scenario: Start soft check when fraud check is passed
    Given account created by user
    And account is under fraud check
    When account passed fraud check
    Then account is under soft check

  Scenario: Account is approved and has positive limit when soft check is passed
    Given account created by user
    And account is under fraud check
    And account passed fraud check
    When account passed soft check
    Then account is approved and has positive limit

  Scenario: Account is rejected when fraud check failed
    Given account created by user
    And account is under fraud check
    When account failed fraud check
    Then account is rejected and limit is zero

  Scenario: Account is rejected when soft check failed
    Given account created by user
    And account is under fraud check
    And account passed fraud check
    When account failed soft check
    Then account is rejected and limit is zero