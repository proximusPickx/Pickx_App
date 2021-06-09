Feature: Live TV Feature 
Description: Live TV related scenario

Background: The user opens the Pickx app and navigates to login page 
Given User opens the PickxTV application 
When The User selects the environment
Then User logged in to the Pickx app
	
@liveTV
Scenario: Validate loading skeleton
Given User navigates to live TV page 
When Validate liveTV loading skeleton
