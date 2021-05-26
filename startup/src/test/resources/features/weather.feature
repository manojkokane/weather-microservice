Feature: Run the Weather Service APIs
			
  Scenario Outline: Call the GET API to get current weather data
		Given ReST Client is setup
		When a request is made to get current weather data with location as "<location>"
		Then the current weather data for location "<location>" is retrieved successfully

		Examples:
			| location                 				|
			| Berlin                	 			|

  Scenario Outline: Call the GET API to get historic weather data
  		Given ReST Client is setup
  		When a request is made to get historic weather data for location "<location>"
  		Then the historic weather data for location "<location>" is retrieved successfully

  		Examples:
  			| location                 				|
  			| Berlin                	 			|