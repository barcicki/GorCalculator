# GoR Calculator #

This is a simple application allowing Go players to calculate how their rating may change during a tournament. It was created to provide a mobile alternative to [GoR calculator on EGD site](http://www.europeangodatabase.eu/EGD/gor_calculator.php).

![ScreenShot](https://www.dropbox.com/s/b1n8wrmed1mycrc/home_screen.png?raw=1)
![ScreenShot](https://www.dropbox.com/s/6ccnqe0c3jp2rc4/game_details.png?raw=1)
![ScreenShot](https://www.dropbox.com/s/uuakow4a02g8qve/downloading.png?raw=1)
![ScreenShot](https://www.dropbox.com/s/y361yf37gny2iv9/player_list.png?raw=1)

## Features ##

*	Real-time calculation of GoR change - no need to refresh browser etc.
* 	Easy way of getting current GoR rating from European Go Database
*	Remembers the players for whom calculations are made
*	Filter the list of players by name, country, club or/and grade

## Download & Requirements ##

[Releases](https://github.com/barcicki/GorCalculator/releases)

*	Android 4.0+
* 	Internet Connection for downloading / updating data from EGD 

## Changelog ##

[Go to changelog](./changelog.md)

## ToDo ##

- [ ] Fetching players' photos from EGD
- [ ] Multiple custom filters
- [ ] Player list sorting

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
