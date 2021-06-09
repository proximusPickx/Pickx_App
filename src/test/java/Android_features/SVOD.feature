Feature: SVOD Feature
Description: SVOD scenario

Background: The user opens the Pickx app and navigates to login page
Given User opens the PickxTV application 
When The User selects the environment
Then User logged in to the Pickx app

@svod
Scenario: SVOD Asset Check
Given User selects recommended in movies and series swimlane
When The user checks the assert available in the svod

@svod1
Scenario: SVOD parental control Asset Check
Given The user sees the locked video content
When The user validates the metadata in locked content