Feature: TV guide Feature
Description: EPG scenario

Background: The user opens the Pickx app and navigates to login page
Given User opens the PickxTV application 
When The User selects the environment
And User logged in to the Pickx app
Then User is on the TV guide page

@TVguide
Scenario: Validate EPG UX
Given The user scroll body horizontally
When User scroll the channel vertically

@TVguide
Scenario: Validate date structure
Given The user clicks on the date picker
When The user validates the date shown in the date picker
Then The user check previous and the future day is shown in the date picker

@TVguide
Scenario: Validate date picker
Given The user clicks on the date picker
When The user validates the program for different dates

@TVguide 
Scenario: Validate non-playable channels UI 
	Given User selects non-playable live program 
	Then Metadata properly displayed for non-playable live program
	
