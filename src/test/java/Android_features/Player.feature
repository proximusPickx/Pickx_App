Feature: Player Feature
Description: Player related scenario

Background: The user opens the Pickx app and navigates to login page
Given User opens the PickxTV application 
When The User selects the environment
Then User logged in to the Pickx app

@Player
Scenario: Validate player live airing portrait and horizontal
Given The user play live airing
When The user see loading page in portrait and horizontal view
Then The user see live airing portrait and horizontal

@Player
@Home
Scenario: Live TV Player controls - User has a Replay subscription 
Given User selects ongoing program from home screen
When Program starts streaming
Then The user validates player control

@Player
@HomeReplayPLus
Scenario: Live TV Player controls - User has a Replay plus subscription 
Given User selects ongoing program from home screen
When Program starts streaming
Then The user validates player control in Replay plus subscription