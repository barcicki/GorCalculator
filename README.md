# GoR Calculator #

This is a simple application allowing Go players to calculate how their rating may change during a tournament. It was created to provide a mobile alternative to [GoR calculator on EGD site](http://www.europeangodatabase.eu/EGD/gor_calculator.php).

## Changelog ##

* 	v.1.0 (2.01.2013)

	Fully working calculator based on rating provided manually
	Basic GUI

## ToDo ##

*	Integration with EGD

	Fetching ratings from European Go Database
	Fetching additional information such as Club, Country and player's photo
	
*	Customization

	Storing recent calculation in memory. Especially the player for whom the calculations are made.
	
## Usage ##

1. Run the app

2. Provide your GoR rating (e.g. 1500 for 6 kyu player),

3. Select tournament class ([reference](http://www.europeangodatabase.eu/EGD/EGF_rating_system.php#CLASS))

4. Add opponents.

	For each opponent specify his GoR rating and game result. If the game involved handicap, click *Details* to provide number of handicaps and color
	
5. After each modification, new rating is calculated. You can see the total change of your rating in the top of screen and the contribution of each game below the opponents' data.   
	
## Licence ##
	
<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/3.0/deed.pl"><img alt="Licencja Creative Commons" style="border-width:0" src="http://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png" /></a>