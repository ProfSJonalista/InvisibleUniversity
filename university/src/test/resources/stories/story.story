Scenario: user opens university folder

Given user is in repository
When user clicks the university folder
Then the university folder is opened and it's not empty


Scenario: user opens src folder

Given user is in university folder and src folder is available
When user clicks the src folder
Then the src folder is opened and it's not empty


Scenario: user opens main folder

Given user is in src folder and main folder is available
When user clicks the main folder
Then the main folder is opened and it's not empty


Scenario: user opens java/invisibleUniversity

Given user is in main folder and java/invisibleUniversity folder is available
When user clicks the invisibleuniversity folder
Then invisibleUniversity folder is opened and it's not empty


Scenario: user opens domain folder

Given user is in java/invisibleUniversity folder and domain folder is available
When user clicks the domain folder
Then domain folder is opened and it's not empty


Scenario: user checks if Creator class exists

Given user is in domain folder and Creator class is available
When user clicks the Creator class
Then class should open
