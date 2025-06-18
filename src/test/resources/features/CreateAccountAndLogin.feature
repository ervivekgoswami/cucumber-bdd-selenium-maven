 @Test
Feature: Create Account and verify login
  As a user I want to create a new account on Magento website
  So that I can login with the credentials used to create new account

  @Test
  Scenario: Open magento testing board application and create new account
    When User clicks on create new account link on homepage
    And User enters first name and last name in personal information
    And User enters email and password in Sign-in Information
    And User clicks on create account button
    Then Verify user is logged into application successfully
    When User sign out from account
    Then User is landed on homepage

  @Test
  Scenario: Login into magento testing board application
    When User clicks on sign in link on homepage
    And User enters login credentials
    And User clicks on Sign-In button
#Then Verify user is logged into application successfully
