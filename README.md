# GoR Calculator #

This is a simple application allowing Go players to calculate how their rating may change during a tournament. It was created to provide a mobile alternative to [GoR calculator on EGD site](http://www.europeangodatabase.eu/EGD/gor_calculator.php).

![ScreenShot](https://www.dropbox.com/sh/6r869ltnzq4yt23/5KL961nXNC/home_screen2.png?dl=1)
![ScreenShot](https://www.dropbox.com/sh/6r869ltnzq4yt23/t57E934H6p/game_details.png?dl=1)
![ScreenShot](https://www.dropbox.com/sh/6r869ltnzq4yt23/d8SFf_LIxq/home_screen3.png?dl=1)
![ScreenShot](https://www.dropbox.com/sh/6r869ltnzq4yt23/-gS8zc_Orj/list_screen1.png?dl=1)

## Features ##

*	Real-time calculation of GoR change - no need to refresh browser etc.
* 	Easy way of getting current GoR rating from European Go Database
*	Remembers the players for whom calculations are made
*	Filter the list of players by name, country, club or/and grade

## Download & Requirements ##

[GorCalculator v.0.9.8](https://www.dropbox.com/s/rutrbcis2wsw82t/GorCalculator_v0.9.8.apk?dl=1)

*	Android 4.0+
* 	Internet Connection for downloading / updating data from EGD 

## Changelog ##

#### v.0.9.8 (3.02.2013) ####
* Fixed issue with deleting opponents
* Replaced tournament name with date of its creation
* Issue: tests fails as Robolectric can't use database (though calculations are ok)

#### v.0.9.7 (3.02.2013) ####
* Added possibility to store multiple tournaments
* Changed the way the data is stored on mobile phones
* *(v.0.9.7.1)* Added marking currently active tournament

#### v.0.9.6 (28.01.2013) ####
* Fixed resuming application and rotating issue
* Improved animation
* Saving tournament class
* Added landscape mode

#### v.0.9.5 (19.01.2013) ####
* Improved country filter
* Solved issue with aborting downloading or parsing

#### v.0.9.4 (18.01.2013) ####
* Country selector in players list
* Switch player list from EUROPE to WORLD (in future it will be customizable) 

#### v.0.9.3 (16.01.2013) ####
* Fixed issue with calculating even games
* Added tests (thanks to Wysek)
* Improved code readability
	
#### v.0.9.2 (14.01.2013) ####
* Added range filter for grade
* Some GUI improvements

#### v.0.9.1 (7.01.2013) ####
* Current players and opponents are automatically saved

#### v.0.9 (6.01.2013) BETA ####
* Added integration with EGD.
* Improved usability and layout.
	
#### v.0.8 (2.01.2013) ####
* Fully working calculator based on rating provided manually
* Basic GUI

## ToDo ##

#### More complex integration with EGD ####
* Fetching players' photos
* Allowing partial downloads, such as players from selected countries
* Allowing partial updates
	
#### More complex customization ####
* **DONE** Allowing to store few profile with different players for whom calculations are made.
* Store multiple custom filters
* **DONE** Improve filters
	
#### Usability improvements ####
* Animations
* Additional features like recently added players etc.
	
#### Sharing ####
* Get social ;)
	
## Usage ##

1. Run the app

2. Find yourself in the database of EGD players. It needs to download the list for the first time (~800 KB of data). You can change / set your GoR rating by clicking the button 

3. Select tournament class ([reference](http://www.europeangodatabase.eu/EGD/EGF_rating_system.php#CLASS))

4. Add opponents.

	For each opponent specify his GoR rating and game result. You can get GoR of yout opponents from EGD by clicking *Find* button. If the game involved handicap, click *Details* to provide number of handicaps and color
		
5. After each modification, new rating is calculated. You can see the total change of your rating in the top of screen and the contribution of each game below the opponents' data.

6. You can click Tournaments button in top-right corner to switch between your tournaments (e.g. to keep results of other players)

7. You can click Update button from menu to update the list from EGD

8. While on the list screen, you can click Save filter button in top-right corner to save current filter as a default one.
 
	
## Licence ##
	
<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/3.0/deed.pl"><img alt="Licencja Creative Commons" style="border-width:0" src="http://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png" /></a>