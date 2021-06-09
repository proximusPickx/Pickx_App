Feature: Home Feature
Description: Home screen related scenario

Background: The user opens the Pickx app and navigates to login page
Given User opens the PickxTV application 
When The User selects the environment
Then User logged in to the Pickx app

@Home
Scenario: Validate Hero banner content
Given Hero content is present on the home screen
When User can see hero banner content are present

@Home
Scenario: Validate bottom navigation menu items
When User can see bottom navigation menu items

@Home12
Scenario: Validate homescreen categories
Given Home screen categories are displayed
When The user validates the displayed categories on the homepage

@Home
Scenario: Validate loading skeleton
Given Validate homescreen loading skeleton

@Home1	
Scenario: Validate parental control
Given User see locked Hero banner
When Tiles are properly locked 
Then User unlocks the locked hero banner