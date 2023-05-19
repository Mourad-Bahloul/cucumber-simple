Feature: Countdown to launch should work
  Scenario: Call backend with last countdown number
    When the client calls endpoint "/launch/counter/4"
    Then response status code is 200
    And returned string should be "T-3"

  Scenario: Call backend with last countdown number 2
    When the client calls endpoint "/launch/counter/1"
    Then response status code is 206
    And returned string should be "Launch Go go go!"

  Scenario: Call backend with last countdown number 3
    When the client calls endpoint "/launch/counterOGIC/1"
    Then response status code is 404
    And returned string should be "Launch Go go go!"

