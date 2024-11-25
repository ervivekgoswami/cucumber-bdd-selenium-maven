Feature: Create Account and verify login
  As a user I want to create a new account on Magento website
  So that I can login with the credentials used to create new account

  Scenario Outline: 1. Open magento testing board application and create new account
    Given User launches magento testing board application
    When User clicks on create new account link on homepage
    And User enters personal information as "<FirstName>" and "<LastName>"
    And User enters Sign-in Information as "<Email>" and "<Password>"
    And User clicks on creat account button
    Then Verify user is logged into application successfully
    When User sign out from account
    Then User is landed on homepage

    Examples: 
      | FirstName | LastName | Email               | Password          |
      | FNewName  | LNewName | UserEmail3@test.com | StrongPassword@12 |

  Scenario: 2. Login into magento testing board application
    Given User launches magento testing board application
    When User clicks on sign in link on homepage
    And User enters credentials "UserEmail3@test.com" and "StrongPassword@12"
    And User clicks on Sign-In button
    Then Verify user is logged into application successfully
