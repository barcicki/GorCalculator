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

[GorCalculator v.0.9.12](https://www.dropbox.com/s/8fgwu71uwoto3j8/GorCalculator_v0.9.12.apk?dl=1)

*	Android 4.0+
* 	Internet Connection for downloading / updating data from EGD 

## Changelog ##

#### v.0.9.12 (8.02.2013) RC2 ####
* Fixed issue with customizing rating of the opponent
* New tournament clones current player but no opponents
* Improved displaying localized number of handicaps

#### v.0.9.11 (7.02.2013) RC2 ####
* Fixed issue with updating database
* Improved GUI on selection items
* Needs to reinstall (loses all data)

#### v.0.9.10 (4.02.2013) RC ####
* Fixed issue with deleting non-last opponent
* Added polish translation

[Previous changes](https://github.com/barcicki/GorCalculator/wiki/Changelog)

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