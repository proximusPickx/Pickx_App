Feature: Login Features
Description: Login related scenario

Background: The user opens the Pickx app and navigates to login page
Given User opens the PickxTV application
When The User selects the environment

@Regression
Scenario: Fresh install to check login page
Given User select the login option
When User is on the login page

@Regression
@mainlogin
Scenario: The user successfully login to Pickx app
Given User select the login option
When User is on the login page
And The User enters credentials
Then User successfully log in to the Pickx app

@Regression
@loginepicuser
Scenario: The user successfully login to Pickx app as a epic user
When The user login as epic user

@Regression
@login
Scenario Outline: Test user Login with different invalid credentials
Given invalid "<username>" and "<password>" is entered
Then User should get an error message
 
Examples:
    | username   | password |
    | pickx@gmail.com | test1234 |
    | pickxuser+vla2@gmail.com | Test1234 |