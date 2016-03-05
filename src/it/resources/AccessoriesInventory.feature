@AccessoriesInventory
Feature: Get an inventory of accessories for computers category

	Scenario: List accessories for computers category
	
	Given a list of accessories is available for category
	| accessories | category  |
	| Mouse       | computers |
	| Mousepad    | computers |
	| Keyboard    | computers |
	| Cable       | computers |
	| Monitor     | computers |
	
	When a GET /accessories is invoked
	
	Then a JSON resonse is returned
	"""
	[
		{'accessories':'Mouse','category':'computers'},
		{'accessories':'Mousepad','category':'computers'},
		{'accessories':'Keyboard','category':'computers'},
		{'accessories':'Cable','category':'computers'},
		{'accessories':'Monitor','category':'computers'}
	]
	"""